import com.google.gson.Gson;
import com.google.gson.JsonObject;
import custom.messages.Message;
import custom.messages.MessageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quickfix.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Demo {
    private static int numberSkip = 0;
    private static int i = 0;
    private static DataDictionary dataDictionary;
    private static Iterator<Field<?>> iterator;
    private static List<JsonObject> listJsonData;
    private static JsonObject jsonObject;
    static Logger logger = LoggerFactory.getLogger(Demo.class);

    public static void main(String[] args) {
        logger.info("Start");
        long startTime = System.currentTimeMillis();
        Gson gson = new Gson();
        try {
            dataDictionary = new DataDictionary("spec/CustomFIX.xml");
            Stream<String> stream = Files.lines(Paths.get("infogateclient.20200805.dat"));
            stream.skip(numberSkip).forEach(str -> {
                try {
                    str += ("10=" + MessageUtils.checksum(str) + "\u0001");
                    Message message = (Message) MessageUtils.parse(new MessageFactory(), dataDictionary, str);
                    listJsonData = new ArrayList<>();
                    jsonObject = new JsonObject();
                    iterator = message.iterator();
                    while (iterator.hasNext()) {
                        Field<?> field = iterator.next();
                        jsonObject.addProperty(dataDictionary.getFieldName(field.getField()) != null ? dataDictionary.getFieldName(field.getField()) : "" + field.getTag() + "", (String) field.getObject());
                    }
                    listJsonData.add(jsonObject);
                    Iterator<Integer> iteratorKeys = message.groupKeyIterator();
                    int count, key;
                    while (iteratorKeys.hasNext()) {
                        key = iteratorKeys.next();
                        for (Group group : message.getGroups(key)) {
                            iterator = group.iterator();
                            jsonObject = new JsonObject();
                            while (iterator.hasNext()) {
                                Field<?> field = iterator.next();
                                jsonObject.addProperty(dataDictionary.getFieldName(field.getField()) != null ? dataDictionary.getFieldName(field.getField()) : "" + field.getTag() + "", (String) field.getObject());
                            }
                            listJsonData.add(jsonObject);
                        }
                    }
                    i++;
                } catch (InvalidMessage invalidMessage) {
                    logger.error("" + invalidMessage + ";at " + invalidMessage.getStackTrace()[0]);
                } catch (Exception exception) {
                    logger.error(""+exception+";at "+ exception.getStackTrace()[0]);
                }
            });
            logger.info("Number of Message: " + i);
        } catch (IOException e) {
            logger.error("" + e + ";at " + e.getStackTrace()[0]);
        } catch (ConfigError configError) {
            logger.error("" + configError + ";at " + configError.getStackTrace()[0]);
        } catch (Exception exception) {
            logger.error("" + exception + ";at " + exception.getStackTrace()[0]);
        }
        long endTime = System.currentTimeMillis();
        logger.info("Time finished process file: " + (endTime - startTime));
        logger.info("Finished");
    }
}
