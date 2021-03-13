package io.kabir.validationexample.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@Validated
public class HomeResource {

    private final Logger log = LoggerFactory.getLogger(HomeResource.class);

    @GetMapping("/day-number")
    public String getDayNumber(@RequestParam @Min(value = 1, message = "Day number must be greater than or equal to 1") @Max(value = 7, message = "Day number has to be less than or equal to 7") Integer dayNumber) {
        return dayNumber.toString();
    }

    @GetMapping("/{tenantId}/grants")
    public String getConsents(@PathVariable String tenantId,
                                       @RequestParam(value = "include", required = true) String include,
                                       @RequestParam(value = "periodStart", required = false) String periodStart,
                                       @RequestParam(value = "scope", required = false) String scope) {
        log.info("Logger Message => tenantId: {}, Include: {}, PeriodStart: {}, Scope: {}", tenantId, include, periodStart, scope);
        return "";
    }
}
