package kitchenpos.fixture;

import kitchenpos.domain.Menu;
import kitchenpos.domain.MenuProduct;
import kitchenpos.domain.MenuProducts;
import kitchenpos.domain.Product;

import java.math.BigDecimal;
import java.util.List;

import static kitchenpos.fixture.MenuGroupFixture.MENU_GROUP2;
import static kitchenpos.fixture.ProductFixture.PRODUCT_1;
import static kitchenpos.fixture.ProductFixture.PRODUCT_2;
import static kitchenpos.fixture.ProductFixture.PRODUCT_3;
import static kitchenpos.fixture.ProductFixture.PRODUCT_4;
import static kitchenpos.fixture.ProductFixture.PRODUCT_5;
import static kitchenpos.fixture.ProductFixture.PRODUCT_6;

public class MenuFixture {

    public static Menu MENU_1 =
            new Menu("후라이드치킨", new BigDecimal("16000.00"), MENU_GROUP2,
                    new MenuProducts(List.of(new MenuProduct(null, null, PRODUCT_1, 1))));
    public static Menu MENU_2 = new Menu("양념치킨", new BigDecimal("16000.00"), MENU_GROUP2,
            new MenuProducts(List.of(new MenuProduct(null, null, PRODUCT_2, 1))));
    public static Menu MENU_3 = new Menu("반반치킨", new BigDecimal("16000.00"), MENU_GROUP2,
            new MenuProducts(List.of(new MenuProduct(null, null, PRODUCT_3, 1))));
    public static Menu MENU_4 = new Menu("통구이", new BigDecimal("16000.00"), MENU_GROUP2,
            new MenuProducts(List.of(new MenuProduct(null, null, PRODUCT_4, 1))));
    public static Menu MENU_5 = new Menu("간장치킨", new BigDecimal("17000.00"), MENU_GROUP2,
            new MenuProducts(List.of(new MenuProduct(null, null, PRODUCT_5, 1))));
    public static Menu MENU_6 = new Menu("순살치킨", new BigDecimal("17000.00"), MENU_GROUP2,
            new MenuProducts(List.of(new MenuProduct(null, null, PRODUCT_6, 1))));
    private MenuFixture() {
    }

    public static Menu MENU_1(Product product) {
        MenuProducts menuProducts = new MenuProducts(List.of(new MenuProduct(null, null, product, 1)));
        Menu 후라이드치킨 = new Menu("후라이드치킨", new BigDecimal("16000.00"), MENU_GROUP2, menuProducts);
        menuProducts.setMenu(후라이드치킨);
        return 후라이드치킨;
    }
}
