package ua.rozetka.webdriver.ProductObject;

public class EntityGoods {

    public String NameGoods;
    public String PriceGoods;

    public EntityGoods(String nameGoods, String priceGoods) {
        NameGoods = nameGoods;
        PriceGoods = priceGoods;
    }

    public String getNameGoods() {
        return NameGoods;
    }

    public void setNameGoods(String nameGoods) {
        NameGoods = nameGoods;
    }

    public String getPriceGoods() {
        return PriceGoods;
    }

    public void setPriceGoods(String priceGoods) {
        PriceGoods = priceGoods;
    }

}
