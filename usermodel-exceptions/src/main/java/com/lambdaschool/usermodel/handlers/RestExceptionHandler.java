package com.lambdaschool.usermodel.handlers;

import com.lambdaschool.usermodel.exceptions.ResourceFoundException;
import com.lambdaschool.usermodel.exceptions.ResourceNotFoundException;
import com.lambdaschool.usermodel.models.ErrorDetail;
import com.lambdaschool.usermodel.services.HelperFunctions;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.Date;

//22 extend an abstract class from Spring called Response EntityExceptionHandler

/**
 * This is the driving class when an exception occurs. All exceptions are handled here.
 * This class is shared across all controllers due to the annotation RestControllerAdvice;
 * this class gives advice to all controllers on how to handle exceptions.
 * Due to the annotation Order(Ordered.HIGHEST_PRECEDENCE), this class takes precedence over all other controller advisors.
 */
//24. If we get an exception, everything needs to stop because the flow of the app is interrupted
// . so no matter what controllers there are make sure this one has the highest Precedence so it
// runs first and if something is wronng it triggers and stops everything.
@Order(Ordered.HIGHEST_PRECEDENCE)
//23 RestControllerAdvice- when an exception happens spring is going to see if there is any
// advice in our rest controllers how to handle said exceptions.
@RestControllerAdvice
public class RestExceptionHandler
        extends ResponseEntityExceptionHandler
{
    /**
     * Connects this class with the Helper Functions
     */
//    *********** Part of 34 **************
//            go back to 34 when autowire is done
    @Autowired
    private HelperFunctions helperFunctions;

    /**
     * The constructor for the RestExceptionHandler. Currently we do not do anything special. We just call the parent constructor.
     */
//   25. Generate a Constructor and call super within it.
    public RestExceptionHandler()
    {
        super();
    }

    /**
     * Our custom handling of ResourceNotFoundExceptions. This gets thrown manually by our application.
     *
     * @param rnfe All the information about the exception that is thrown.
     * @return The error details for displaying to the client plus the status Not Found.
     */
//    41. we want to handle our exception ResourceNotFoundException differently with annotation
//    ExceptionHandler, then put any valid Exception, this is how you pin point which exception
//    you want to handle.  this ExceptionHandler starts out right above step 26. but we add the
//    following things to make it so far above.
    @ExceptionHandler(ResourceNotFoundException.class)
    //    42. like we said earlier its a response so its a response entity, everything that handles
//    an exception starts with handle and then the name of the exception. because spring is doing
//    things for me we get to pick the parameter of ResourseNotFounexception(rnfe) and call it rnfe,
//    so the exception that gets thrown by jave we capture it here and doe something with it.
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rnfe)
    {
//        44. we use what is about to build error detail. just like we did before. they can be
//        so similar  java will say you have duplicate code, but you dont.
            ErrorDetail errorDetail = new ErrorDetail();
//      45.     Set the Time
        errorDetail.setTimestamp(new Date());
//      46.  Set the status and our case we are using the Status of Not_Found, and the value of it.
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
//      47. Error message Title
        errorDetail.setTitle("Resource Not Found");
//      48.  Error Message
        errorDetail.setDetail(rnfe.getMessage());
//      49. add developer Message.
        errorDetail.setDeveloperMessage(rnfe.getClass()
                                                .getName());
//      50. fill in validation errors just in case we need them. and now we created our custome
//      method to handle or custom exception.
        errorDetail.setErrors(helperFunctions.getConstraintViolation(rnfe));
//      51. Run the app and test it out by trigggering a message.
//      52. go to applicatin properties
//      43. Create your Return as below we want the error detail, no headers, and HttStatus Not
//      Found(404)
        return new ResponseEntity<>(errorDetail,
                                    null,
                                    HttpStatus.NOT_FOUND);
    }

    /**
     * Our custom handling of ResourceFoundExceptions. This gets thrown manually by our application.
     *
     * @param rfe All the information about the exception that is thrown.
     * @return The error details for displaying to the client plus the status Bad Request.
     */

    @ExceptionHandler(ResourceFoundException.class)
    public ResponseEntity<?> handleResourceFoundException(ResourceFoundException rfe)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetail.setTitle("Unexpected Resource");
        errorDetail.setDetail(rfe.getMessage());
        errorDetail.setDeveloperMessage(rfe.getClass()
                                                .getName());
        errorDetail.setErrors(helperFunctions.getConstraintViolation(rfe));

        return new ResponseEntity<>(errorDetail,
                                    null,
                                    HttpStatus.BAD_REQUEST);
    }

    /**
     * All other exceptions not handled elsewhere are handled by this method.
     *
     * @param ex      The actual exception used to get error messages
     * @param body    The body of this request. Not used in this method.
     * @param headers Headers that are involved in this request. Not used in this method.
     * @param status  The Http Status generated by the exception. Forwarded to the client.
     * @param request The request that was made by the client. Not used in this method.
     * @return The error details to display to the client plus the status that from the exception.
     */

//    26. next we need to make an override. click generate override methods, a bunch of options
//    show up.  first pick handleExceptionInternal, get rid of return because we will write our own.
    @Override
//    object and question mark within<> are the same thing. Object is more backwards compatible
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request)
    {
//  27. we want to send back the results once the exception happens so we send out an error detail
        ErrorDetail errorDetail = new ErrorDetail();
//        29. first set timestamp
        errorDetail.setTimestamp(new Date());
//        30. next setStatus, which is given to us but we need it in its int form so we need its
//        value.
        errorDetail.setStatus(status.value());
//        31. Set Generic Title, there are also.gets that you can add to this that gives you
//        different info
        errorDetail.setTitle("Rest Internal Exception");
//        32. Set Message, which is the details of the errors.
        errorDetail.setDetail(ex.getMessage());
//        33. what type of message do we want to send back, which is the class that is causing
//        the problem, this might help or not, the crucial info we give back is the value and
//        message. and a list of errors.

        errorDetail.setDeveloperMessage(ex.getClass()
                                                .getName());
//        34. set the list of errors, and we already have a method helping us with this that we
//        made at the beinning, inside of helperFunctions called getConstraintViolation. First we
//        wire that in at the top then come back and finish this call. ex is the exception. we
//        could of done this.getError(WebRequest) if we wanted to but since Spring has already
//        done it for us we just use ex. we can check now to see if our exceptions work, run the
//        app and in postman do an invalid request to see the new error lay out.

//        35. Next we want to fancy up the user id not found error message so its more specific,
//        in order to find this we have to figure it where its coming from and what method its
//        using, in our case we go to our controllers, User Controller, one of the endpoints in
//        controller is to look for a user base off of id that endpoint ultimately calls find
//        user by id from user service so go to user service impl.

        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));
//28. we want to return errorDetail, we want it to return a status as well. next fill in error
// detail.
        return new ResponseEntity<>(errorDetail,
                                    null,
                                    status);
    }

//    ********************************Extra Notes/Code*********************************************
    /*********************
     * The rest of the methods are not required and so are provided for reference only.
     * They allow you to better customized exception messages
     ********************/

    /**
     * Reports when a correct endpoint is accessed but with an unsupported Http Method.
     *
     * @param ex      The actual exception used to get error messages
     * @param headers Headers that are involved in this request. Not used in this method.
     * @param status  The Http Status generated by the exception. Forwarded to the client.
     * @param request The request that was made by the client.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Incorrect method: " + ex.getMethod());
        errorDetail.setDetail("Path: " + request.getDescription(false) + " | Supported Methods are: " + Arrays.toString(ex.getSupportedMethods()));
        errorDetail.setDeveloperMessage("HTTP Method Not Valid for Endpoint (check for valid URI and proper HTTP Method)");
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
                                    null,
                                    status);
    }

    /**
     * Reports when a correct endpoint is accessed but with an unsupported content, media, type.
     *
     * @param ex      The actual exception used to get error messages
     * @param headers Headers that are involved in this request. Not used in this method.
     * @param status  The Http Status generated by the exception. Forwarded to the client.
     * @param request The request that was made by the client.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Incorrect content type: " + ex.getContentType());
        errorDetail.setDetail("Path: " + request.getDescription(false) + " | Supported Content / Media Types are: " + ex.getSupportedMediaTypes());
        errorDetail.setDeveloperMessage("Content / Media Type Not Valid for Endpoint (check for valid URI and proper content / media type)");
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
                                    null,
                                    status);
    }

    /**
     * Reports when a correct endpoint is accessed but with an unacceptable content, media, type.
     * An unacceptable content, media type is one that server does not support at all, regardless of endpoints.
     * Normally the error is unsupported type, so this rarely happens.
     *
     * @param ex      The actual exception used to get error messages
     * @param headers Headers that are involved in this request. Not used in this method.
     * @param status  The Http Status generated by the exception. Forwarded to the client.
     * @param request The request that was made by the client.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
            HttpMediaTypeNotAcceptableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Unacceptable content type: " + ex.getMessage());
        errorDetail.setDetail("Path: " + request.getDescription(false) + " | Supported Content / Media Types are: " + ex.getSupportedMediaTypes());
        errorDetail.setDeveloperMessage("Content / Media Type Not Valid for Endpoint (check for valid URI and proper content / media type)");
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
                                    null,
                                    status);
    }

    /**
     * If a path variable is missing, however, in this application this normally becomes an unhandled endpoint
     *
     * @param ex      The actual exception used to get error messages
     * @param headers Headers that are involved in this request. Not used in this method.
     * @param status  The Http Status generated by the exception. Forwarded to the client.
     * @param request The request that was made by the client.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(
            MissingPathVariableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle(ex.getVariableName() + " Missing Path Variable");
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setDeveloperMessage(ex.getClass()
                                                .getName());
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
                                    null,
                                    status);
    }

    /**
     * A parameter is missing.
     *
     * @param ex      The actual exception used to get error messages
     * @param headers Headers that are involved in this request. Not used in this method.
     * @param status  The Http Status generated by the exception. Forwarded to the client.
     * @param request The request that was made by the client.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Parameter Missing for " + "Path: " + request.getDescription(false));
        errorDetail.setDetail("Parameter Missing: " + ex.getParameterName() + " Type: " + ex.getParameterType());
        errorDetail.setDeveloperMessage(ex.getMessage() + " " + ex.getClass());
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
                                    null,
                                    status);
    }

    /**
     * Servlet Request Binding Exception.
     *
     * @param ex      The actual exception used to get error messages
     * @param headers Headers that are involved in this request. Not used in this method.
     * @param status  The Http Status generated by the exception. Forwarded to the client.
     * @param request The request that was made by the client.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(
            ServletRequestBindingException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Path: " + request.getDescription(false) + " Request Binding Exception");
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setDeveloperMessage(ex.getClass()
                                                .getName());
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
                                    null,
                                    status);
    }

    /**
     * Conversion Not Supported.
     *
     * @param ex      The actual exception used to get error messages
     * @param headers Headers that are involved in this request. Not used in this method.
     * @param status  The Http Status generated by the exception. Forwarded to the client.
     * @param request The request that was made by the client.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(
            ConversionNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Path: " + request.getDescription(false) + " Conversion Not Support");
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setDeveloperMessage(ex.getClass()
                                                .getName() + " " + ex.getMostSpecificCause());
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
                                    null,
                                    status);
    }

    /**
     * Type Mismatch.
     *
     * @param ex      The actual exception used to get error messages
     * @param headers Headers that are involved in this request. Not used in this method.
     * @param status  The Http Status generated by the exception. Forwarded to the client.
     * @param request The request that was made by the client.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Path: " + request.getDescription(false) + " Type Mismatch");
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setDeveloperMessage(ex.getClass()
                                                .getName() + " " + ex.getMostSpecificCause());
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
                                    null,
                                    status);
    }

    /**
     * Message Not Readable.
     *
     * @param ex      The actual exception used to get error messages
     * @param headers Headers that are involved in this request. Not used in this method.
     * @param status  The Http Status generated by the exception. Forwarded to the client.
     * @param request The request that was made by the client.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Path: " + request.getDescription(false) + " Message Not Readable");
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setDeveloperMessage(ex.getClass()
                                                .getName() + " " + ex.getMostSpecificCause());
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
                                    null,
                                    status);
    }

    /**
     * Message Not Writable.
     *
     * @param ex      The actual exception used to get error messages
     * @param headers Headers that are involved in this request. Not used in this method.
     * @param status  The Http Status generated by the exception. Forwarded to the client.
     * @param request The request that was made by the client.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(
            HttpMessageNotWritableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Path: " + request.getDescription(false) + " Message Not Writable");
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setDeveloperMessage(ex.getClass()
                                                .getName() + " " + ex.getMostSpecificCause());
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
                                    null,
                                    status);
    }

    /**
     * A when an argument fails the @Valid check
     *
     * @param ex      The actual exception used to get error messages
     * @param headers Headers that are involved in this request. Not used in this method.
     * @param status  The Http Status generated by the exception. Forwarded to the client.
     * @param request The request that was made by the client.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Method Argument Not Valid");
        errorDetail.setDetail(request.getDescription(false) + " | parameter: " + ex.getParameter());
        errorDetail.setDeveloperMessage(ex.getBindingResult()
                                                .toString());
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
                                    null,
                                    status);
    }

    /**
     * Missing Servlet Request
     *
     * @param ex      The actual exception used to get error messages
     * @param headers Headers that are involved in this request. Not used in this method.
     * @param status  The Http Status generated by the exception. Forwarded to the client.
     * @param request The request that was made by the client.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(
            MissingServletRequestPartException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle(request.getDescription(false) + " Missing Servlet Request");
        errorDetail.setDetail("Request Part Name: " + ex.getRequestPartName() + " | " + ex.getMessage());
        errorDetail.setDeveloperMessage(ex.getClass()
                                                .getName());
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
                                    null,
                                    status);
    }

    /**
     * Bind Exception
     *
     * @param ex      The actual exception used to get error messages
     * @param headers Headers that are involved in this request. Not used in this method.
     * @param status  The Http Status generated by the exception. Forwarded to the client.
     * @param request The request that was made by the client.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleBindException(
            BindException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Bind Exception");
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setDeveloperMessage(ex.getClass()
                                                .getName() + " " + ex.getBindingResult());
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
                                    null,
                                    status);
    }


    /**
     * Client is trying to access an endpoint that does not exist. Requires additions to the application.properties file.
     * server.error.whitelabel.enabled=false
     * spring.mvc.throw-exception-if-no-handler-found=true
     * spring.resources.add-mappings=false
     *
     * @param ex      The actual exception used to get error messages
     * @param headers Headers that are involved in this request. Not used in this method.
     * @param status  The Http Status generated by the exception. Forwarded to the client.
     * @param request The request that was made by the client.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Rest Endpoint Not Valid");
        errorDetail.setDetail(request.getDescription(false));
        errorDetail.setDeveloperMessage("Rest Handler Not Found (check for valid URI)");
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
                                    null,
                                    status);
    }

    /**
     * An async request has timed out
     *
     * @param ex         The actual exception used to get error messages
     * @param headers    Headers that are involved in this request. Not used in this method.
     * @param status     The Http Status generated by the exception. Forwarded to the client.
     * @param webRequest The request that was made by the client.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(
            AsyncRequestTimeoutException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest webRequest)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Async Request Timeout Error");
        errorDetail.setDetail("path: " + webRequest.getDescription(false));
        errorDetail.setDeveloperMessage(ex.getMessage());
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
                                    null,
                                    status);
    }
}
