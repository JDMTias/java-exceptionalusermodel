package com.lambdaschool.usermodel.exceptions;

/**
 * A custom exception to be used when a resource is not but is suppose to be
 */

public class ResourceNotFoundException
        extends RuntimeException
        // 36. we need to tell the JDK that this is going to be an exception its my own, so extend the
        // RuntimeException.
{
//    there is a bunch of methods that you can override but a normal thing to do is just generate
//    a constructor.
//    37. Next Generate a Constructor, the one we want to generate is the one that sends a
//    message,  in the param we are putting in message wich is the same message from the
//    Userserviceimpl. but we want to add something to it. so before we cre
    public ResourceNotFoundException(String message)
    {
//        38.we want to add something to the "message" so before we create the message we add the
//        message we want in front of it. we have now created a custom exception.
        super("Error from a Lambda School Application " + message);
    }
}
//  39.***************** everywhere that we have entity not found exception, we
//  want to change it to Resource not found exception, we want to do it easily so we can make
//  this change globaly, right click on source, replace in path, in the
//  first line we put EntityNotFoundException. click on result
//  and on the next line we write what we are  replacing it with, ResourceNotFoundException
// click on replace all, then go through the imports and make sure those are changed as well or
// import what you need to import, or you can go to code and click on optimize imports and that
// will automatically do it. you have to do that to each page though just fyi.

//  40. no we run the app and try getting users/user/15 to run it should throw an error, id not
//  found again, and the message above should pop up
//41. Looking at it though we can fancy it up a little bit more, we have a class in handlers
// RestExceptionHandler that handles exceptions go there.
