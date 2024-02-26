const fs = require("fs");

fs.readFile("sample.json", "utf8", (err, data) => {
  if (err) {
    console.error("Error reading JSON file:", err);
    return;
  }

  const jsonData = JSON.parse(data);
  const products = [];
  let categoryId = 0;
  const categoryMap = new Map();
  const productCategoryCrossRefs = [];

  jsonData.products.forEach((product) => {
    if (!categoryMap.has(product.category)) {
      const catId = categoryId++;
      categoryMap[product.category] = {
        categoryId: catId,
        categoryName: product.category,
      };
    }
    const category = categoryMap[product.category];
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
