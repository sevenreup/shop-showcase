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
    products.push({
      productId: product.id,
      name: product.title,
      description: product.description,
      images: product.images,
      price: product.price,
      rating: product.rating,
      brand: product.brand,
      thumbnail: product.thumbnail,
      categoryId: category.categoryId,
    });
  });

  const output = {
    products,
    categories: Object.entries(categoryMap).map((e) => e[1]),
  };

  const modifiedData = JSON.stringify(output, null, 2);

  fs.writeFileSync(
    "../app/src/main/assets/seed-data.json",
    modifiedData,
    "utf8"
  );
});
