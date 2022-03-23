package com.trendyol.linkconverter.services.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static com.trendyol.linkconverter.services.validation.ValidationErrorMessage.ERROR_INVALID_LINK;

/**
 * This validation is used for validation link fields.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LinkValidator.class)
public @interface ValidateLink {
    String message() default ERROR_INVALID_LINK;

    String host() default "";

    String protocol() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
