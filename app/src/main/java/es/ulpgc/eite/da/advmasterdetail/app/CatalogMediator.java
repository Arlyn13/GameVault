package es.ulpgc.eite.da.advmasterdetail.app;

import es.ulpgc.eite.da.advmasterdetail.categories.CategoryListState;
import es.ulpgc.eite.da.advmasterdetail.data.CategoryItem;
import es.ulpgc.eite.da.advmasterdetail.data.ProductItem;
import es.ulpgc.eite.da.advmasterdetail.product.ProductDetailState;
import es.ulpgc.eite.da.advmasterdetail.products.ProductListState;

public class CatalogMediator {

//  private CategoryListState categoryListState = new CategoryListState();
//  private ProductListState productListState = new ProductListState();
//  private ProductDetailState productDetailState = new ProductDetailState();

  private CategoryListState categoryListState;
  private ProductListState productListState;
  private ProductDetailState productDetailState;
  private CategoryItem category;
  private ProductItem product;


  private static CatalogMediator INSTANCE;

  private CatalogMediator() {

  }

  public static void resetInstance() {
    INSTANCE = null;
  }


  public static CatalogMediator getInstance() {
    if(INSTANCE == null){
      INSTANCE = new CatalogMediator();
    }

    return INSTANCE;
  }


  public CategoryListState getCategoryListState() {
    return categoryListState;
  }

  public ProductDetailState getProductDetailState() {
    return productDetailState;
  }

  public ProductListState getProductListState() {
    return productListState;
  }

  public ProductItem getProduct() {
    ProductItem item = product;
    //product = null;
    return item;
  }


  public void setProduct(ProductItem item) {
    product = item;
  }

  public void setCategory(CategoryItem item) {
    category = item;
  }

  public CategoryItem getCategory() {
    CategoryItem item = category;
    //category = null;
    return item;
  }

  public void setCategoryListState(CategoryListState state) {
    categoryListState = state;
  }

  public void setProductListState(ProductListState state) {
    productListState=state;

  }

  public void setProductDetailState(ProductDetailState state) {
    productDetailState=state;
  }
}
