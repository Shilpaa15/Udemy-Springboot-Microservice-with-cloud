package guru.springframework.msscbrewery.web.controller;

import guru.springframework.msscbrewery.services.CustomerService;

import guru.springframework.msscbrewery.web.model.CustomerDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customer")            //Api version v1=> allows u to evolve API without breaking existing API customers
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping({"/{customerId}"})
public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("customerId") UUID customerId){
    return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK);
}

@PostMapping
    public ResponseEntity<CustomerDto> handlePost(@RequestBody CustomerDto customerDto){
    CustomerDto savedDto=customerService.saveNewCustomer(customerDto);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Location", "/api/v/customer/" + savedDto.getId().toString());  //type location inside bracket hearName wil ome automatic
    return new ResponseEntity<>(headers, HttpStatus.CREATED);
}

@PutMapping("/{customerId}")
public ResponseEntity<CustomerDto> handleUpdate(@PathVariable("customerId") UUID customerId,@RequestBody CustomerDto customerDto) {
    customerService.updateCustomer(customerId, customerDto);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}
    @DeleteMapping({"/{customerId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("customerId") UUID customerId){
        customerService.deleteById(customerId);
    }
}
