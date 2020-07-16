package it.progettoOOP.model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class FileTest {
    private File file;
    @BeforeEach
    void setUp() {
        file = new File();
    }

    @AfterEach
    void tearDown() {
    }

    @DisplayName("ricavare estensione dal nome file")
    @Test
    void getTipoFile() {
        Assertions.assertAll(
                () -> assertEquals("File", file.tipoFile("nomeFile")),
                () -> assertEquals("Testo", file.tipoFile("nomeFile.txt")),
                () -> assertEquals("Immagine", file.tipoFile("nomeFile.png")),
                () -> assertEquals("File", file.tipoFile("nomeFile.pdf")),
                () -> assertEquals("File", file.tipoFile("nomeFile.pn")),
                () -> assertEquals("File", file.tipoFile("nomeFile.png.tx"))
        );
    }        File file1 = new File("nomeFile");
        File file2 = new File("nomeFile.txt");
        File file3 = new File("nomeFile.png");
        File file4 = new File("nomeFile.pdf");
        File file5 = new File("nomeFile.pn");
        File file6 = new File("nomeFile.png.tx");
}