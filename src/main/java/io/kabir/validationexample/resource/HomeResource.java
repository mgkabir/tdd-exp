package io.kabir.validationexample.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.xml.ws.Response;
import java.time.LocalDate;

@RestController
@Validated
public class HomeResource {

    private final Logger log = LoggerFactory.getLogger(HomeResource.class);

    @GetMapping("/day-number")
    public String getDayNumber(@RequestParam @Min(value = 1, message = "Day number must be greater than or equal to 1") @Max(value = 7, message = "Day number has to be less than or equal to 7") Integer dayNumber) {
        return dayNumber.toString();
    }

    @GetMapping("/{tenantId}/grants")
    public ResponseEntity<String> getItems(@PathVariable String tenantId,
                                              @RequestParam(value = "include", required = true) String include,
                                              @RequestParam(value = "periodStart", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate periodStart,
                                              @RequestParam(value = "periodEnd", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate periodEnd,
                                              @RequestParam(value = "scope", required = false) String scope) {
        log.info("Logger Message => tenantId: {}, Include: {}, PeriodStart: {}, PeriodEnd: {} ,Scope: {}", tenantId, include, periodStart, periodEnd, scope);

        if (periodStart != null & periodEnd != null && periodStart.isAfter(periodEnd))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Period start cannot be after Period end");

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
