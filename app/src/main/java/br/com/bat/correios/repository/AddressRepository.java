package br.com.bat.correios.repository;

import br.com.bat.correios.model.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, String> {
}
