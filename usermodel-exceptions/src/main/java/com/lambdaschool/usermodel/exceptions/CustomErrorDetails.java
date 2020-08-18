package com.lambdaschool.usermodel.exceptions;


import com.lambdaschool.usermodel.services.HelperFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.LinkedHashMap;
import java.util.Map;
//11. here we are creating a lay out for those errors that we didn't handle and Spring Boot is
// handling. Instead of its default
/**
 * Class to override the error details provided by Spring Boot. We want to use our own format.
 */
// create abstract class, extends DefaultErrorAttributes. make sure Spring knows about it so make
// it a component this changes it from a pojo to a bean
@Component
public class CustomErrorDetails
        extends DefaultErrorAttributes
{
    /**
     * Connects this class with the Helper Functions
     */
//    18. Autowire in the HelperFunciton services this allows us to use the method we created in
//    services in this class. after this we are done with CustomeErrorDetails. go to 19 in
//    HelperFunctions.
    @Autowired
    private HelperFunctions helperFunctions;

    /**
     * Custom method to override the error details provided by Spring Boot. We want to use our own format.
     *
     * @param webRequest        The information related to the request that was made and the exceptions that occurred.
     * @param includeStackTrace Should we include the Stack Trace in our output. This field is not used in our implementation.
     * @return a Map of String, Object with our information to report in place of the standard Spring Boot information.
     */
//    12. we need to overide a method, and we want to overide the Get error attributes that
//    returns a map. INtelliJ can generate this btw. delete the generated return line so it won't
//    cause problems later on and we will make our own return line.
    @Override
    public Map<String, Object> getErrorAttributes(
            WebRequest webRequest,
            boolean includeStackTrace)
    {

        //13.Get all the normal error information(attributes) that Spring knows about.
        Map<String, Object> errorAttributes =
                super.getErrorAttributes(webRequest, includeStackTrace);
//        14. creating our lay out.
        // Linked HashMaps maintain the order the items are inserted. I am using it here so that the error JSON
        // produced from this class lists the attributes in the same order as other classes.
        Map<String, Object> errorDetails = new LinkedHashMap<>();
//        15. we need to look at error detail to see what we need to bring in here..and in the
//        same order that appears in error detail model. "title" is the field, then "error" is
//        the value. the title is the error itself, the status is from status, the detail is the
//        message, timestamp is timestamp, and the devmessage is where this error sends back the
//        path where the error message occurred.
        errorDetails.put("title", errorAttributes.get("error"));
        errorDetails.put("status", errorAttributes.get("status"));
        errorDetails.put("detail", errorAttributes.get("message"));
        errorDetails.put("timestamp", errorAttributes.get("timestamp"));
        errorDetails.put("developerMessage", "path: " + errorAttributes.get("path"));
//  17. creating the list of the validation errors. we will have to create this in multiple
//  places but we don't want to just copy and paste so we want to create a method to have arror
//  details put errors  and call a helper function that is going to get the validation constraints,
//  the ones that are invalid. Web request is what contains the actual merror message.
//  ConstraintIolations method and helper functions service have not been made yet so we are
//  going to do this next under services create an interface called HelperFunctions.
        errorDetails.put("errors", helperFunctions.getConstraintViolation(this.getError(webRequest)));
//    16.  return your errorDetails from line 51.
        return errorDetails;
    }
}
