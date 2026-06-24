package helper;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class MonacoEditorHelper {

    public static void inputMonacoEditor(WebDriver driver, String code) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("""
                const text = arguments[0];
    
                if (!window.monaco || !monaco.editor || !monaco.editor.getModels().length) {
                    throw new Error("Monaco editor model not found");
                }
    
                const models = monaco.editor.getModels();
    
                // Prefer JSON model if there are multiple editors
                const model = models.find(m => m.getLanguageId && m.getLanguageId() === "json") || models[0];
    
                model.setValue(text);
            """, code);
    }

    public static String getMonacoEditor(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        String currentValue = (String) js.executeScript("""
            const models = monaco.editor.getModels();
            const model = models.find(m => m.getLanguageId && m.getLanguageId() === "json") || models[0];
            return model.getValue();
        """);

        return currentValue;
    }
}
