package salon.lab5;

import static salon.lab5.classes.Request.resetIds;

import java.util.*;

import salon.lab5.classes.FemaleRequest;
import salon.lab5.classes.MaleRequest;
import salon.lab5.classes.Request;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.text.Text;

public class Habitat {
    // женские заявки
    private Vector<FemaleRequest> requestsFemale = new Vector<>();
    // Мужские заявки
    private Vector<MaleRequest> requestsMale = new Vector<>();

    // уникальные идентификаторы хранятся в TreeSet
    private HashSet<Integer> uniqueIds = new HashSet<>();

    // Коллекция для хранения объектов по времени рождения
    private TreeMap<Long, Request> birthTimeMap = new TreeMap<>();

    // объекты по времени рождения хранятся в HashMap
    private HashMap<Integer, Long> birthTimes = new HashMap<>();

    private boolean simulationRunning;  // флаг работы симуляции
    private long simulationStartTime;   // начало работы симуляции

    private Timer timer;    // таймер
    private Timer simulationTimer;

    private Random random = new Random();


    private static final int FIELD_WIDTH = 800; // Ширина области визуализации
    private static final int FIELD_HEIGHT = 600; // Высота области визуализации


    // задаем интервалы в секундах
    private static final int FEMALE_GENERATION_INTERVAL = 10;
    private static final int MALE_GENERATION_INTERVAL = 5;
    // задаем вероятности создания заявок
    private static final int FEMALE_PROBABILITY = 66;
    private static final int MALE_PROBABILITY = 33;


    private Pane visualisationPane;

    private Text timeText;
    private Text statText;

    private static String imagePathFemale = "/salon/female.jpg";
    private static String imagePathMale = "/salon/male.jpg";


    public Habitat(){
        // создать коллекции с пустыми ячейками
        this.requestsFemale = new Vector<>();
        this.requestsMale = new Vector<>();
        this.birthTimeMap = new TreeMap<>();
        this.uniqueIds = new HashSet<>();
        //this.timer = new Timer();

        this.simulationRunning=false;
        this.simulationStartTime=0;
    }

    public Habitat(Pane visualisationPane, Text timeText, Text statText){
        // создать коллекции с пустыми ячейками
        this.requestsFemale = new Vector<>();
        this.requestsMale = new Vector<>();
        this.birthTimeMap = new TreeMap<>();
        this.uniqueIds = new HashSet<>();
        //this.timer = new Timer();

        this.simulationRunning=false;
        this.simulationStartTime=0;

        // передаем экземпляр класса visualisationPane в конструктор класса поведения Habitat
        this.visualisationPane = visualisationPane;
        this.timeText = timeText;
        this.statText = statText;
    }

    // если событие произошло то возвращается true
    private boolean isEventHappened(int probability) {
        return new Random().nextInt(100) < probability;
    }


    // запуск симуляции с генерацией объектов и их обработкой
    public void startSimulation(){

        if (!simulationRunning) {
            simulationRunning = true;
            simulationStartTime=System.currentTimeMillis();
            startTimer();

        }
        //System.out.println("Начинаю симуляцию...");


        // вызываем по таймеру метод Update
    }



    public void update(long elapsedTime){

        System.out.println(elapsedTime / 1000); // вывести в консоль время
        long time = elapsedTime / 1000; // переводим время в секунды


        // обновляем время
        updateTime(elapsedTime);

        // генерация новых объектов и помещение их в поле визуала

        if (time % 3 == 0) {    // каждую 3-ую секунду пробуем
            if (isEventHappened(FEMALE_PROBABILITY)){   // генерировать заявку в женский зал
                    FemaleRequest femaleRequest = new FemaleRequest();

                    femaleRequest.setXY(random.nextInt(FIELD_WIDTH), random.nextInt(FIELD_HEIGHT)); // задаем случаные координаты для заявки в границах поля

                    // Добавляем в коллекцию заявку
                    requestsFemale.add(femaleRequest);
                    birthTimeMap.put(System.currentTimeMillis(), femaleRequest);
                    uniqueIds.add(femaleRequest.hashCode());


                    // добавление объекта в визуализацию
                    visualizeObject(femaleRequest);
                }
        }

        if (time % 5 == 0){
            if (isEventHappened(MALE_PROBABILITY)){
                    MaleRequest maleRequest = new MaleRequest();

                    maleRequest.setXY(random.nextInt(FIELD_WIDTH), random.nextInt(FIELD_HEIGHT));


                    // Добавляем в коллекию заявку
                    requestsMale.add(maleRequest);
                    birthTimeMap.put(System.currentTimeMillis(), maleRequest);
                    uniqueIds.add(maleRequest.hashCode());



                    // добавление объекта в визуализацию
                    visualizeObject(maleRequest);
                }
        }
    }


    public void startKeyboardListener(){
        Scanner scanner = new Scanner(System.in);
        while (true){
            if (scanner.hasNext()){
                String key = scanner.next();
                switch (key.toUpperCase()){
                    case "B":
                    startSimulation();
                    break;
                    case "E":
                    stopSimulation();
                    break;
                    default:
                    System.out.println("Не знаю такой кнопки");
                }
            }
        }

    }


    private void addObjectToPane(String imagePath) {
        // Загружаем изображение
        Image image = new Image(getClass().getResourceAsStream(imagePath));

        // Создаем ImageView для отображения изображения
        ImageView imageView = new ImageView(image);

        // Устанавливаем размеры изображения
        imageView.setFitWidth(100); // Ширина изображения
        imageView.setFitHeight(100); // Высота изображения

        Bounds bounds = visualisationPane.getBoundsInLocal();
        double minX = bounds.getMinX();
        double minY = bounds.getMinY();
        double maxX = bounds.getMaxX() - imageView.getFitWidth(); // Максимальное значение x для изображения
        double maxY = bounds.getMaxY() - imageView.getFitHeight(); // Максимальное значение y для изображения

        // Генерируем случайные координаты для расположения изображения в пределах панели
        double randomX = Math.random() * (maxX - minX) + minX;
        double randomY = Math.random() * (maxY - minY) + minY;

        // Устанавливаем положение изображения на панели
        imageView.setLayoutX(randomX);
        imageView.setLayoutY(randomY);

        // Добавляем ImageView на панель visualisationPane
        visualisationPane.getChildren().add(imageView);
    }



    private void updateTime(long elapsedTime){
        Platform.runLater(() -> {
            //ваш код
            timeText.setText(elapsedTime / 1000 + " секунд"); // Обновляем текст элемента timeText

        });
    }



    private void visualizeObject(Request request){
        if (request instanceof FemaleRequest) { // проверяем является ли request femaleRequest
            // тогда делаем что-то для femaleRequest
            Platform.runLater(() -> {
                addObjectToPane(imagePathFemale);

            });

            System.out.println("Создана заявка в женский зал");
            System.out.println(request);

            // отладка
            System.out.println(request.hashCode());

        }
        else if (request instanceof MaleRequest) {
            // Тогда делаем что-то для maleRequest
            Platform.runLater(() -> {
                addObjectToPane(imagePathMale);
            });

            System.out.println("Создана заявка в мужской зал");
            System.out.println(request);

            // отладка
            System.out.println(request.hashCode());

        }
        else {
            System.out.println("Неизвестная заявка была создана");
            System.out.println(request);
        }
    }

    public void stopSimulation(){
        // код для остановки симуляции
        if (simulationRunning){
            simulationRunning = false;
            stopTimer();
            System.out.println("Симуляция остановлена");

            // сначала очищаем сцену
            visualisationPane.getChildren().clear();
            // выводим статистику
            getFinalStatistics();
            // clear collections
            clearCollections();




        }
    }

    public void clearCollections(){
        birthTimeMap.clear();
        resetIds();

        uniqueIds.clear();
        requestsFemale.clear();
        requestsMale.clear();


    }


    private void startTimer(){
        if (simulationTimer == null){
            simulationTimer = new Timer();
            simulationTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    long currentTime = System.currentTimeMillis();  // текущее время берется с системного
                    long elapsedTime = currentTime - simulationStartTime;   // прошедшее время = текущее - время старта симуляции
                    update(elapsedTime);    // вызываем основной метод симуляции - Update

                }
            }, 0, 1000); // Здесь 1000 миллисекунд (1 секунда) - интервал таймера

        }
    }

    // метод остановки таймера
    private void stopTimer(){
        if (simulationTimer != null){
            simulationTimer.cancel();
            simulationTimer = null;
        }
    }

    //public void displayInfo(){
        // вывод информации о количестве и типе сгенерированных объектов
        // а также время симуляции в поле визуала
    //}

    // вывод финальной статистики
    public void getFinalStatistics(){

        String statistics = "Статистика симуляции:\n" +
            "Женские заявки: " + requestsFemale.size() + "\n" +
            "Мужские заявки: " + requestsMale.size() + "\n" +

            "Время симуляции: " + timeText.getText();

            //"Уникальные идентификаторы: " + uniqueIds.size() + "\n" +



        for (Map.Entry<Long, Request> entry : birthTimeMap.entrySet()) {
            Long key = entry.getKey();
            Request value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + value.getId());
        }


        Platform.runLater(() -> {
            //ваш код
            statText.setText(statistics);

        });



        System.out.println(statistics);

    }
}
