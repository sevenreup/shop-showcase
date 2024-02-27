const fs = require("fs");

fs.readFile("sample.json", "utf8", (err, data) => {
  if (err) {
    console.error("Error reading JSON file:", err);
    return;
  }

  const jsonData = JSON.parse(data);
  const products = [];
  let categoryId = 1;
  const categoryMap = {};
  const productCategoryCrossRefs = [];

  jsonData.products.forEach((product) => {
    let category = categoryMap[product.category];

    if (category == undefined || category == null) {
      category = {
        categoryId: categoryId,
        categoryName: product.category,
      };
      categoryMap[product.category] = category;
      categoryId = categoryId + 1;
    }
    productCategoryCrossRefs.push({
      productId: product.id,
      categoryId: category.categoryId,
    });
    products.push({
      productId: product.id,
      name: product.title,
      description: product.description,
      images: product.images,
      price: product.price,
      rating: product.rating,
      brand: product.brand,
      thumbnail: product.thumbnail,
      categories: [category.categoryId],
    });
  });

  const output = {
    products,
    categories: Object.entries(categoryMap).map((e) => e[1]),
    productCategoryCrossRefs,
  };

  const modifiedData = JSON.stringify(output, null, 2);

  fs.writeFileSync(
    "../app/src/main/assets/seed-data.json",
    modifiedData,
    "utf8"
  );
});
