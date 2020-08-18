package com.lambdaschool.usermodel.models;
//this is a list of errors that joins with Validatin error in error detail.
//

//notice there is no constructor, we will let the JDK handle that for us

/**
 * A model to report a validation error
 */
public class ValidationError
{
    /**
     * The code (String) for the validation error
     */
    private String Code;

    /**
     * The message (String) from the validation error
     */
    private String message;

    /**
     * Getter for the code
     *
     * @return the code (String) for this validation error
     */
    public String getCode()
    {
        return Code;
    }

    /**
     * Setter for the code
     *
     * @param code the new code (String) for this validation error
     */
    public void setCode(String code)
    {
        Code = code;
    }

    /**
     * Getter for the message
     *
     * @return The message (String) associated with this validation error
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * Setter for the message
     *
     * @param message The new message (String) associated with this validation error
     */
    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * Displays the current validation error
     *
     * @return The current validation error as a String
     */

//    stop here go to error detail
    @Override
    public String toString()
    {
        return "ValidationError{" + "Code='" + Code + '\'' + ", message='" + message + '\'' + '}';
    }
}
