package io.kabir.validationexample.resource;

import io.kabir.validationexample.model.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AddressResource {

    private final Logger log = LoggerFactory.getLogger(AddressResource.class);
    @PostMapping("/address")
    public ResponseEntity<String> addAddress(@Valid @RequestBody Address address){
        log.info("Address : {} ",address);
        return ResponseEntity.ok("OK");
    }
}
