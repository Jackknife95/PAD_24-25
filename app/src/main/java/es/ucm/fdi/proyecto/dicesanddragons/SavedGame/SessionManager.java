package es.ucm.fdi.proyecto.dicesanddragons.SavedGame;

public class SessionManager {
    private static SessionManager instance;
    private SavedGame currentSession;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public SavedGame getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(SavedGame session) {
        this.currentSession = session;
    }


}
