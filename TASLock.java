import java.util.concurrent.atomic.AtomicBoolean;


public class TASLock 
{

    private final AtomicBoolean locked = new AtomicBoolean(false);
    private int testAndSetCount = 0;

    /* Do not modify this method */
    private boolean testAndSet() 
    {
        return locked.getAndSet(true);
    }

    public void lock() 
{
    while (true)
    {
        testAndSetCount++;

        if (!testAndSet())
        {
            return;
        }
    }
}

    public void unlock() 
    {
        locked.set(false);
    }

    public int getTestAndSetCount()
{
    return testAndSetCount;
}
    
}