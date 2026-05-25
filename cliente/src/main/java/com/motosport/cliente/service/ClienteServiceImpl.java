package com.motosport.cliente.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.motosport.cliente.dto.ClienteDto;
import com.motosport.cliente.dto.ResponseDto;
import com.motosport.cliente.model.Cliente;
import com.motosport.cliente.repository.ClienteRepository;


@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repository;

    public ClienteServiceImpl(ClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    public ClienteDto addCliente(ClienteDto dto) {
        Cliente cliente = repository.save(dtoToModel(dto));
        return modelToDto(cliente);
    }

    @Override
    public ClienteDto getCliente(Long id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        return modelToDto(cliente);
    }

    @Override
    public List<ClienteDto> getAllCliente() {
        List<Cliente> clientes = repository.findAll();

        return clientes.stream()
                .map(this::modelToDto)
                .toList();
    }

    @Override
    public ClienteDto updateCliente(Long id, ClienteDto dto) {

        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        cliente.setRut(dto.rut());
        cliente.setNombre(dto.nombre());
        cliente.setApellidos(dto.apellidos());
        cliente.setnumeroTelefono(dto.numeroTelefono());
        cliente.setCorreo(dto.correo());
        cliente.setNroLicencia(dto.nroLicencia());
        cliente.setFechaVencimiento(dto.fechaVencimiento());
        cliente.setFechaRegistro(dto.fechaRegistro());

        repository.save(cliente);

        return modelToDto(cliente);
    }

    @Override
    public ResponseDto deleteCliente(Long id) {

        if (repository.existsById(id)) {
            repository.deleteById(id);
            return new ResponseDto("Cliente eliminado correctamente.");
        } else {
            return new ResponseDto("Cliente no encontrado.");
        }
    }

    private ClienteDto modelToDto(Cliente model) {

        return new ClienteDto(
                model.getId(),
                model.getRut(),
                model.getNombre(),
                model.getApellidos(),
                model.getnumeroTelefono(),
                model.getCorreo(),
                model.getNroLicencia(),
                model.getFechaVencimiento(),
                model.getFechaRegistro()
        );
    }

    private Cliente dtoToModel(ClienteDto dto) {

        Cliente cliente = new Cliente();

        cliente.setId(dto.id());
        cliente.setRut(dto.rut());
        cliente.setNombre(dto.nombre());
        cliente.setApellidos(dto.apellidos());
        cliente.setnumeroTelefono(dto.numeroTelefono());
        cliente.setCorreo(dto.correo());
        cliente.setNroLicencia(dto.nroLicencia());
        cliente.setFechaVencimiento(dto.fechaVencimiento());
        cliente.setFechaRegistro(dto.fechaRegistro());

        return cliente;
    }
}