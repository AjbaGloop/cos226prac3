import java.util.concurrent.atomic.AtomicBoolean;

public class TTASLock 
{

    private final AtomicBoolean locked = new AtomicBoolean(false);
     private int testAndSetCount = 0;

    /* Do not modify this method */
    private boolean testandTestAndSet() 
    {
        return locked.getAndSet(true);
    }

    public void lock() 
    {

        while (true) {
            // spin here reading the flag (no writes, so no cache-line
            // invalidation traffic while the lock is held by someone else)
            while (locked.get()) {
                // busy-wait
            }

             testAndSetCount++;

            // the lock looked free — now actually try to grab it
            if (!testandTestAndSet()) {
                // getAndSet returned false, meaning it was free and is
                // now ours
                return;
            }
            // someone beat us to it; go back to spin-reading
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