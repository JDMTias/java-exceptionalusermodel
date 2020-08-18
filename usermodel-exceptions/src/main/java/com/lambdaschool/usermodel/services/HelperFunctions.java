package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.models.ValidationError;

import java.util.List;

/**
 * Class contains helper functions - functions that are needed throughout the application. The class can be autowired
 * into any class.
 */
// 17. creating Helperfunctions interface
public interface HelperFunctions
{
//    this takes all of the things that comeback from the web request and find all of the
//    violations and bring them back to this list.
    /**
     * Searches to see if the exception has any constraint violations to report
     *
     * @param cause the exception to search
     * @return constraint violations formatted for sending to the client
     */
    List<ValidationError> getConstraintViolation(Throwable cause);


}
// 18 once done Autowire HelperFunctions into CustomerErrorDetails then come back
//19. now we need to implement  this service so create a HelperFunctionsImpl class. 
