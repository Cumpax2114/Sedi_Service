package dev.franklinbg.sediservice.repository;

import dev.franklinbg.sediservice.entity.Proveedor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProveedorRepository extends CrudRepository<Proveedor, Integer> {
    @Query(value = "SELECT * FROM Proveedor WHERE estado = 'A' ", nativeQuery = true)
    Iterable<Proveedor> listActivos();
}
