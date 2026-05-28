package es.ulpgc.eite.da.advmasterdetail.data;

import java.util.List;

public interface RepositoryContract {

  interface FetchCatalogDataCallback {
    void onCatalogDataFetched(boolean error);
  }

  interface GetProductListCallback {
    void setProductList(List<ProductItem> products);
  }

  interface GetProductCallback {
    void setProduct(ProductItem product);
  }

  interface GetCategoryListCallback {
    void setCategoryList(List<CategoryItem> categories);
  }

  interface GetCategoryCallback {
    void setCategory(CategoryItem category);
  }

  interface DeleteCategoryCallback {
    void onCategoryDeleted();
  }

  interface UpdateCategoryCallback {
    void onCategoryUpdated();
  }

  interface DeleteProductCallback {
    void onProductDeleted();
  }

  interface UpdateProductCallback {
    void onProductUpdated();
  }


  void loadCatalog(
      boolean clearFirst, CatalogRepository.FetchCatalogDataCallback callback);

  void getProductList(
      CategoryItem category, CatalogRepository.GetProductListCallback callback);

  void getProductList(
      int categoryId, CatalogRepository.GetProductListCallback callback);

  void getProduct(int id, CatalogRepository.GetProductCallback callback);
  void getCategory(int id, CatalogRepository.GetCategoryCallback callback);
  void getCategoryList(CatalogRepository.GetCategoryListCallback callback);

  void deleteProduct(
      ProductItem product, CatalogRepository.DeleteProductCallback callback);

  void updateProduct(
      ProductItem product, CatalogRepository.UpdateProductCallback callback);

  void deleteCategory(
      CategoryItem category, CatalogRepository.DeleteCategoryCallback callback);

  void updateCategory(
      CategoryItem category, CatalogRepository.UpdateCategoryCallback callback);
}
