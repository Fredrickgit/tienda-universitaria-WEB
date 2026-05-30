package com.unimag.tiendauniversitaria.config;

import com.unimag.tiendauniversitaria.entity.Category;
import com.unimag.tiendauniversitaria.entity.Address;
import com.unimag.tiendauniversitaria.entity.Customer;
import com.unimag.tiendauniversitaria.entity.Inventory;
import com.unimag.tiendauniversitaria.entity.Product;
import com.unimag.tiendauniversitaria.enums.CustomerStatus;
import com.unimag.tiendauniversitaria.repository.AddressRepository;
import com.unimag.tiendauniversitaria.repository.CategoryRepository;
import com.unimag.tiendauniversitaria.repository.CustomerRepository;
import com.unimag.tiendauniversitaria.repository.InventoryRepository;
import com.unimag.tiendauniversitaria.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DemoDataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    @Override
    @Transactional
    public void run(String... args) {
        Category papeleria = getOrCreateCategory(
                "Papeleria",
                "Utiles y materiales para clases, apuntes y trabajo diario."
        );
        Category tecnologia = getOrCreateCategory(
                "Tecnologia",
                "Accesorios y herramientas digitales para estudiantes."
        );
        Category merchandising = getOrCreateCategory(
                "Merchandising",
                "Productos institucionales y articulos de identidad universitaria."
        );

        Product libreta = getOrCreateProduct(
                "PAP-LIB-001",
                "Libreta Universitaria A5",
                "Libreta de 100 hojas rayadas para apuntes, practicas y organizacion de materias.",
                new BigDecimal("12500.00"),
                papeleria
        );
        getOrCreateInventory(libreta, 45, 8);

        Product memoria = getOrCreateProduct(
                "TEC-USB-032",
                "Memoria USB 32GB",
                "Unidad USB compacta para guardar trabajos, presentaciones y material academico.",
                new BigDecimal("28900.00"),
                tecnologia
        );
        getOrCreateInventory(memoria, 22, 5);

        Product camiseta = getOrCreateProduct(
                "MER-CAM-001",
                "Camiseta Institucional",
                "Camiseta tipo casual con diseno universitario para eventos y actividades.",
                new BigDecimal("45000.00"),
                merchandising
        );
        getOrCreateInventory(camiseta, 16, 4);

        Customer ana = getOrCreateCustomer("Ana", "Martinez", "ana.martinez@demo.com", CustomerStatus.ACTIVE);
        getOrCreateDefaultAddress(ana, "Carrera 32 #12-45", "Santa Marta", "Magdalena", "470001");

        Customer carlos = getOrCreateCustomer("Carlos", "Rojas", "carlos.rojas@demo.com", CustomerStatus.ACTIVE);
        getOrCreateDefaultAddress(carlos, "Calle 18 #4-22", "Santa Marta", "Magdalena", "470002");

        Customer laura = getOrCreateCustomer("Laura", "Gomez", "laura.gomez@demo.com", CustomerStatus.INACTIVE);
        getOrCreateDefaultAddress(laura, "Avenida Libertador #20-10", "Santa Marta", "Magdalena", "470003");
    }

    private Category getOrCreateCategory(String name, String description) {
        return categoryRepository.findByName(name)
                .orElseGet(() -> categoryRepository.save(Category.builder()
                        .name(name)
                        .description(description)
                        .build()));
    }

    private Product getOrCreateProduct(
            String sku,
            String name,
            String description,
            BigDecimal price,
            Category category
    ) {
        return productRepository.findBySku(sku)
                .orElseGet(() -> productRepository.save(Product.builder()
                        .sku(sku)
                        .name(name)
                        .description(description)
                        .price(price)
                        .active(true)
                        .category(category)
                        .build()));
    }

    private void getOrCreateInventory(Product product, Integer availableStock, Integer minimumStock) {
        inventoryRepository.findByProductId(product.getId())
                .orElseGet(() -> inventoryRepository.save(Inventory.builder()
                        .product(product)
                        .availableStock(availableStock)
                        .minimumStock(minimumStock)
                        .build()));
    }

    private Customer getOrCreateCustomer(
            String firstName,
            String lastName,
            String email,
            CustomerStatus status
    ) {
        return customerRepository.findByEmail(email)
                .orElseGet(() -> customerRepository.save(Customer.builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .email(email)
                        .status(status)
                        .build()));
    }

    private void getOrCreateDefaultAddress(
            Customer customer,
            String street,
            String city,
            String department,
            String postalCode
    ) {
        if (!addressRepository.findByCustomerId(customer.getId()).isEmpty()) {
            return;
        }

        addressRepository.save(Address.builder()
                .customer(customer)
                .street(street)
                .city(city)
                .department(department)
                .postalCode(postalCode)
                .isDefault(true)
                .build());
    }
}
