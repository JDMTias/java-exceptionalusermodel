package com.lambdaschool.usermodel.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A class used to display error messages in our own chosen format
 */
public class ErrorDetail
{
    /**
     * The title (String) of the error message
     */
//    Second, we want to give our Error a title.
    private String title;

    /**
     * HTTP Status of this error
     */
//    the number that goes along with the error.
    private int status;

    /**
     * Detailed message for an end user, client, explaining the error
     */
//    3
    private String detail;

    /**
     * Date and time stamp for this error
     */
//    4
    private Date timestamp;

    /**
     * A message for developers about this error message, things like class and code causing the error.
     * Specifically written so as not to give away security information.
     */
//    5
    private String developerMessage;

    /**
     * If data validation errors caused this error, the list of them will appear here.
     */
//   6.
//   if we get a json object from a client that has invalid fields, we want to send back a list
//    of all the fields that are invalid.
    private List<ValidationError> errors = new ArrayList<>();
//7
//    here we go create validation error and comeback after. Make a new model called Validation
//    Error.
    /**
     * Default constructor for this class
     */
//    this constructor he doesnt add at first
    public ErrorDetail()
    {
    }

    /**
     * Getter for title
     *
     * @return Title (String) of this error
     */
//    8 set getters and setters. go to bottom of page once done.
    public String getTitle()
    {
        return title;
    }

    /**
     * Setter for title
     *
     * @param title the new title (String) for this error
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * Getter for status
     *
     * @return the Http Status code (int) for this error
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * Setter for status
     *
     * @param status The new Http Status code (int) for this error
     */
    public void setStatus(int status)
    {
        this.status = status;
    }

    /**
     * Getter for error details
     *
     * @return A detailed message (String) about this error suitable for regular users, clients
     */
    public String getDetail()
    {
        return detail;
    }

    /**
     * Setter for error details
     *
     * @param detail The new detailed message (String) about this error suitable for regular users, clients
     */
    public void setDetail(String detail)
    {
        this.detail = detail;
    }

    /**
     * Getter for the date and time when this error happened
     *
     * @return The data and time (date) when this error happened
     */
    public Date getTimestamp()
    {
        return timestamp;
    }

    /**
     * Setter for the date and time when this error happened
     *
     * @param timestamp the changed data and time (date) when this error happened
     */
    public void setTimestamp(Date timestamp)
    {
        this.timestamp = timestamp;
    }

    /**
     * Getter for the developer's message
     *
     * @return A message for developers about this error message (String), things like class and code causing the error.
     */
    public String getDeveloperMessage()
    {
        return developerMessage;
    }

    /**
     * Setter for the developer's message
     *
     * @param developerMessage The new message for developers about this error message (String), things like class and code causing the error.
     */
    public void setDeveloperMessage(String developerMessage)
    {
        this.developerMessage = developerMessage;
    }

    /**
     * Getter for the list of validation errors
     *
     * @return The list of validation errors, if any, for this error
     */
    public List<ValidationError> getErrors()
    {
        return errors;
    }

    /**
     * Setter for the list of validation errors
     *
     * @param errors The new list of validation errors, if any, for this error
     */
    public void setErrors(List<ValidationError> errors)
    {
        this.errors = errors;
    }
}

// 9. now although we have told spring boot not to autmatically handle things and let us handle
// the exception, if we forgot an exception it will still automatically run it for us, kinda like
// a big brother saying het you forgot something its ok ill handle it for you, so we have to say
// ok if that is the case i wan't you to handle it in a certain format
//10. create a package called exceptions. Inside this package we are going to make different
// types of exceptions.
// 11. in exceptions "pagagge" create class CustomErrorDetails
