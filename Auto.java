import java.util.Arrays;
import java.util.Random;

public class Auto implements Vehicle{
    private String mark;
    private Model[] models;

    public String getMark() {
        return mark;
    }

    public void setMark(String value) {
        this.mark = value;
    }

    public class Model {
        private String name;
        private double price;

        public Model(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String value) {
            this.name = value;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double value) {
            this.price = value;
        }
    }

    public Auto(String mark, int countModels) {
        this.mark = mark;
        this.models = new Model[countModels];
        Random rnd = new Random();

        for (int i = 0; i < countModels; i++) {
            models[i] = new Model("Модель" + (i + 1), rnd.nextDouble(0, 1000000));
        }
    }

    private int findIndex(String name) throws NoSuchModelNameException {
        int index = -1;

        for (int i = 0; i < models.length && index == -1; i++) {
            if (name != null && name.equals(models[i].getName())) {
                index = i;
            }
        }

        if (index == -1) {
            throw new NoSuchModelNameException(name);
        }

        return index;
    }

    public void setModelName(String oldName, String newName)
            throws NoSuchModelNameException, DuplicateModelNameException{
        int index = -1;


        for (int i = 0; i < models.length; i++) {
            String name = models[i].getName();
            if (oldName != null && oldName.equals(name)) {
                index = i;
            } else if (newName != null && newName.equals(name)) {
                throw new DuplicateModelNameException(newName);
            }
        }

        if (index == -1) {
            throw new NoSuchModelNameException(oldName);
        }

        models[index].setName(newName);
    }

    public String[] getArrayModelNames() {
        String[] names = new String[models.length];

        for (int i = 0; i < models.length; i++) {
            names[i] = models[i].getName();
        }

        return names;
    }

    public double getPriceByName(String name) throws NoSuchModelNameException{
        int index = findIndex(name);
        double price = models[index].getPrice();
        return price;
    }

    public void setPriceByName(String name, double newPrice) throws NoSuchModelNameException{

        if (newPrice < 0) {
            throw new ModelPriceOutOfBoundsException(newPrice);
        }
        int index = findIndex(name);
        models[index].setPrice(newPrice);
    }

    public double[] getArrayModelPrices() {
        double[] prices = new double[models.length];

        for (int i = 0; i < models.length; i++) {
            prices[i] = models[i].getPrice();
        }

        return prices;
    }

    public void addModel(String name, double price) throws DuplicateModelNameException {
        if (price < 0) {
            throw new ModelPriceOutOfBoundsException(price);
        }

        for (int i = 0; i < models.length; i++) {
            if (name.equals(models[i].getName())) {
                throw new DuplicateModelNameException(name);
            }
        }
        models = Arrays.copyOf(models, models.length + 1);
        models[models.length - 1] = new Model(name, price);
    }

    public void removeModel(String name) throws NoSuchModelNameException{
        int index = findIndex(name);
        System.arraycopy(models, index + 1, models, index, models.length - index - 1);
        models = Arrays.copyOf(models, models.length - 1);
    }

    public int getSize() {
        return models.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Автомобиль марки: ").append(mark).append("\n");
        for (int i = 0; i < models.length; i++) {
            sb.append("  ").append(models[i].getName())
                    .append(" — ").append(models[i].getPrice()).append("\n");
        }
        return sb.toString();
    }
}

