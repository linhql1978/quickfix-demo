import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.quickfixj.codegenerator.MessageCodeGenerator;
import org.quickfixj.codegenerator.MessageCodeGenerator.Task;

import java.io.File;

public class MyGenerator {
    public static void main(String[] args) {
////        if (args.length == 0) {
////            System.err.println("Must supply base package name");
////        }
////        String basePackage = "";
//        Task task = new Task();
//        task.setName("Custom 4.4");
//        task.setSpecification(new File("spec/CustomFIX.xml"));
//        task.setTransformDirectory(new File("codeGenerator"));
//        task.setOutputBaseDirectory(new File("src/main/java"));
//        task.setMessagePackage("custom.messages");
//        task.setFieldPackage("custom.fields");
//        MessageCodeGenerator codeGenerator = new MessageCodeGenerator();
//        codeGenerator.generate(task);
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("123", "abc");
//        System.out.println(gson.toJson(jsonObject));
        jsonObject.addProperty("123", "abcd");
        System.out.println(gson.toJson(jsonObject));
    }
}
