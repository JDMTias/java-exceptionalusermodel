package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.models.CountryData;
import com.lambdaschool.usermodel.models.ValidationError;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

//19. Creating implementation. its going to implement HelperFunctions. it is a service so
// @Service and give it the value the same name as what we called it in Error Details.

@Service(value = "helperFunctions")
public class HelperFunctionsImpl
        implements HelperFunctions
{
    /**
     * A public field used to store data from another API. This will have to be populated each time the application is run.
     * Population is done manually for each country code using an endpoint.
     */
    public static CountryData ourCountryData = new CountryData();

//    20. THis started with an OVerride in the lecture...not here now...maybe it got changed
//    later on in the lecture.  This will contain all the exceptions, but we want to loop through
//    all the exceptions that show up and find the ones that correspond with validation, until
//    either you run out of exceptions, or i find one that is of constraintViolationException.

    public List<ValidationError> getConstraintViolation(Throwable cause)
    {
//      20.
        // Find any data violations that might be associated with the error and report them
        // data validations get wrapped in other exceptions as we work through the Spring
        // exception chain. Hence we have to search the entire Spring Exception Stack
        // to see if we have any violation constraints.
        while ((cause != null) && !(cause instanceof ConstraintViolationException))
        {
            cause = cause.getCause();
        }

        List<ValidationError> listVE = new ArrayList<>();

        // we know that cause either null or an instance of ConstraintViolationException
//        if the cause from above is  not null that means we found a constraintViolation, so we
//        need to loop through my ConstraintViolationException and add them to the list. Go
//        through loop. add them to the listVE.
        if (cause != null)
        {
            ConstraintViolationException ex = (ConstraintViolationException) cause;
            for (ConstraintViolation cv : ex.getConstraintViolations())
            {
                ValidationError newVe = new ValidationError();
                newVe.setCode(cv.getInvalidValue()
                                      .toString());
                newVe.setMessage(cv.getMessage());
                listVE.add(newVe);
            }
        }
// 21. return the new list.
        return listVE;
    }
}

// 22 22 22 22 22 22 after finishing this class we are going to go make use of the  model class
// ErrorDetail. we are going to create a bean with a method inside of it that spring can use to
// generically errors. This will handle most of the exceptions we run into. Since this is going
// to be handling our exceptions we create a pagagge that we call Handlers that will handle
// exceptions. Next everything we are doing in here is rest, a client sends us a request in the
// process of processing that request there will be an exception and we have to handle that
// exception. Create a class that is going to be called RestExceptionHandler and this will handle
// of the rest of the exceptions.

//better explanation of loop in the method below.
//we have getConstraintViolation method, we bring in to it some list of exceptions(cause), loop
// through exceptions to see if we find one that is of Constraint Violation Exception, create a
// list that we are going to return, if we did find one that is of CVE great then loop through
// all of the CVE that are in that CVE and add them to the list, if you didn't find one then just
// return an empty list.

