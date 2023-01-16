import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    private final Map<String, List<PageEntry>> wordsLists = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException {

        for (File pdfDir : pdfsDir.listFiles()) {
            var doc = new PdfDocument(new PdfReader(pdfDir));   // создать объект пдф-документа

            for (int i = 1; i < doc.getNumberOfPages(); i++) {
                var text = PdfTextExtractor.getTextFromPage(doc.getPage(i));    // получить текст со страницы
                var words = text.split("\\P{IsAlphabetic}+");   // разбить текст на слова

                Map<String, Integer> freqs = new HashMap<>(); // ключ - слово, а значение - частота

                for (var word : words) { // перебираем слова
                    if (word.isEmpty()) {
                        continue;
                    }
                    word = word.toLowerCase();
                    freqs.put(word, freqs.getOrDefault(word, 0) + 1);
                }

                for (Map.Entry<String, Integer> entry : freqs.entrySet()) {
                    List<PageEntry> result;
                    if (wordsLists.containsKey(entry.getKey())) {
                        result = wordsLists.get(entry.getKey());
                    } else {
                        result = new ArrayList<>();
                    }
                    result.add(new PageEntry(pdfDir.getName(), i, entry.getValue()));
                    wordsLists.put(entry.getKey(), result);
                }
            }
        }
        wordsLists.values().forEach(Collections::sort);
    }

    @Override
    public List<PageEntry> search(String word) {
        return wordsLists.get(word.toLowerCase());
    }
}
