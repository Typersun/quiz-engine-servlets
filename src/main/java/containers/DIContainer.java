package containers;

import com.fasterxml.jackson.databind.ObjectMapper;
import repositories.impl.QuizRepositoryImpl;
import repositories.impl.UserRepositoryImpl;

import java.util.HashMap;
import java.util.Map;

public class DIContainer {
    public static DIContainer instance = new DIContainer();
    private final Map<String, Object> dependencies = new HashMap<>();

    private DIContainer() {
        addDependency("QuizRepository", new QuizRepositoryImpl());
        addDependency("ObjectMapper", new ObjectMapper());
        addDependency("QuestionRepository", new QuizRepositoryImpl());
        addDependency("UserRepository", new UserRepositoryImpl());
    }

    public void addDependency(String key, Object dependency) {
        dependencies.put(key, dependency);
    }

    public Object getDependency(String key) {
        return dependencies.get(key);
    }
}
