package quoters;

public class ProfilingController implements ProfilingControllerBean{

    private boolean enabled;

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
