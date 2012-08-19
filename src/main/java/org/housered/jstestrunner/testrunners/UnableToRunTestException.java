package org.housered.jstestrunner.testrunners;

public class UnableToRunTestException extends Exception
{

    private static final long serialVersionUID = 1913020545640574739L;

    public UnableToRunTestException(Exception e)
    {
        super(e);
    }

}
