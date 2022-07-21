import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskManager {
    private TaskManager(){
        executor = Executors.newFixedThreadPool(5);
    }

    private static TaskManager instance;
    private ExecutorService executor;

    public synchronized static TaskManager getInstance(){
        if(instance == null){
            instance = new TaskManager();
        }
        return instance;
    }

    public ExecutorService getExecutor(){
        return executor;
    }
}
