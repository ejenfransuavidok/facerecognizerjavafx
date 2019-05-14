package sample;

public enum Scenes {

    FIRST_SCENE(0, "/FXML/first.fxml", "Загрузка скан-копии паспорта"),
    SECOND_SCENE(1, "/FXML/second.fxml", "Распознавание через видеосъемку"),
    THIRD_SCENE(2, "/FXML/third.fxml", "Результат");

    int id;

    String template;

    String title;

    Scenes(int id, String template, String title) {
        this.id = id;
        this.template = template;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTemplate() {
        return template;
    }

    public String getTitle() {
        return title;
    }

}
