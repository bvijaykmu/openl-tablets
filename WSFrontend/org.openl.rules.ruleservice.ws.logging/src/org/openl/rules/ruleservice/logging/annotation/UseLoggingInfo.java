package org.openl.rules.ruleservice.logging.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.openl.rules.project.model.RulesDeploy.PublisherType;
import org.openl.rules.ruleservice.logging.LoggingInfoConvertor;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UseLoggingInfo {
    Class<? extends LoggingInfoConvertor<?>> convertor();
    PublisherType[] publisherTypes() default {PublisherType.WEBSERVICE, PublisherType.RESTFUL};
}
