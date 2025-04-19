package br.com.fiap.locatech.locatech.services;

import br.com.fiap.locatech.locatech.dtos.AluguelRequestDTO;
import br.com.fiap.locatech.locatech.entities.Aluguel;
import br.com.fiap.locatech.locatech.exceptions.ResourceNotFoundException;
import br.com.fiap.locatech.locatech.repositories.AluguelRepository;
import br.com.fiap.locatech.locatech.repositories.VeiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AluguelService {

    private final AluguelRepository aluguelRepository;
    private final VeiculoRepository veiculoRepository;

    public AluguelService(AluguelRepository aluguelRepository, VeiculoRepository veiculoRepository) {
        this.aluguelRepository = aluguelRepository;
        this.veiculoRepository = veiculoRepository;
    }

    public Optional<Aluguel> findById(Long id) {
        return Optional.ofNullable(aluguelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluguel não encontrado")));
    }

    public List<Aluguel> findAllAlugueis(int page, int size) {

        int offset = (page - 1) * size;
        return this.aluguelRepository.findAll(size, offset);
    }

    public void saveAluguel(AluguelRequestDTO aluguel) {

        var aluguelEntity = this.calcularAluguel(aluguel);
        var save = this.aluguelRepository.save(aluguelEntity);
        Assert.state(save == 1, "Erro ao salvar aluguel" + aluguel.pessoaId());
    }

    public void updateAluguel(Aluguel aluguel, Long id) {

        var update = this.aluguelRepository.update(aluguel, id);
        if (update == 0) {
            throw new ResourceNotFoundException("Aluguel não encontrado");
        }
    }

    public void deleteAluguel(Long id) {

        var delete = this.aluguelRepository.delete(id);
        if (delete == 0) {
            throw new ResourceNotFoundException("Aluguel não encontrado");
        }
    }

    private Aluguel calcularAluguel(AluguelRequestDTO aluguelRequestDTO) {

        var veiculo = this.veiculoRepository.findById(aluguelRequestDTO.veiculoId())
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado"));

        var quantidadeDias = BigDecimal.valueOf(aluguelRequestDTO.dataFim().getDayOfYear() - aluguelRequestDTO.dataInicio().getDayOfYear());
        var valor = veiculo.getValorDiaria().multiply(quantidadeDias);
        return new Aluguel(aluguelRequestDTO, valor);
    }
}