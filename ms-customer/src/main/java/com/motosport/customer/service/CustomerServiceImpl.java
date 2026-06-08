package com.motosport.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.motosport.customer.dto.CustomerDto;
import com.motosport.customer.dto.ResponseDto;
import com.motosport.customer.model.Customer;
import com.motosport.customer.repository.CustomerRepository;


@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public CustomerDto addCustomer(CustomerDto dto) {
        Customer customer = repository.save(dtoToModel(dto));
        return modelToDto(customer);
    }

    @Override
    public CustomerDto getCustomer(Long id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer no encontrado"));

        return modelToDto(customer);
    }

    @Override
    public List<CustomerDto> getAllCustomer() {
        List<Customer> customers = repository.findAll();

        return customers.stream()
                .map(this::modelToDto)
                .toList();
    }

    @Override
    public CustomerDto updateCustomer(Long id, CustomerDto dto) {

        Customer customer = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer no encontrado"));

        customer.setRut(dto.rut());
        customer.setNombre(dto.nombre());
        customer.setApellidos(dto.apellidos());
        customer.setnumeroTelefono(dto.numeroTelefono());
        customer.setCorreo(dto.correo());
        customer.setNroLicencia(dto.nroLicencia());
        customer.setFechaVencimiento(dto.fechaVencimiento());
        customer.setFechaRegistro(dto.fechaRegistro());

        repository.save(customer);

        return modelToDto(customer);
    }

    @Override
    public ResponseDto deleteCustomer(Long id) {

        if (repository.existsById(id)) {
            repository.deleteById(id);
            return new ResponseDto("Customer eliminado correctamente.");
        } else {
            return new ResponseDto("Customer no encontrado.");
        }
    }

    private CustomerDto modelToDto(Customer model) {

        return new CustomerDto(
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

    private Customer dtoToModel(CustomerDto dto) {

        Customer customer = new Customer();

        customer.setId(dto.id());
        customer.setRut(dto.rut());
        customer.setNombre(dto.nombre());
        customer.setApellidos(dto.apellidos());
        customer.setnumeroTelefono(dto.numeroTelefono());
        customer.setCorreo(dto.correo());
        customer.setNroLicencia(dto.nroLicencia());
        customer.setFechaVencimiento(dto.fechaVencimiento());
        customer.setFechaRegistro(dto.fechaRegistro());

        return customer;
    }
}