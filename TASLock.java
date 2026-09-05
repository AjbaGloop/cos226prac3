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
<<<<<<< Updated upstream
        
=======
        testAndSetCount++;

        if (!testAndSet())
        {
            return;
        }
>>>>>>> Stashed changes
    }
}

    public void unlock() 
    {
        
    }

    public int getTestAndSetCount()
{
    return testAndSetCount;
}
    
}