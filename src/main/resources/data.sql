
ALTER TABLE payment_transactions MODIFY COLUMN status VARCHAR(20);


-- Insert Categories
INSERT INTO categories (id, name, slug, description, parent_id, created_at, updated_at) VALUES
(1, 'Giày thể thao', 'giay-the-thao', 'Các loại giày thể thao chính hãng', NULL, NOW(), NOW()),
(2, 'Giày bóng rổ', 'giay-bong-ro', 'Giày bóng rổ chuyên nghiệp', NULL, NOW(), NOW()),
(3, 'Giày chạy bộ', 'giay-chay-bo', 'Giày chạy bộ công nghệ cao', NULL, NOW(), NOW()),
(4, 'Giày đá bóng', 'giay-da-bong', 'Giày đá bóng chuyên nghiệp', NULL, NOW(), NOW()),
(5, 'Giày tennis', 'giay-tennis', 'Giày tennis cao cấp', NULL, NOW(), NOW()),
(6, 'Giày tập gym', 'giay-tap-gym', 'Giày tập gym chuyên dụng', NULL, NOW(), NOW()),
(7, 'Giày leo núi', 'giay-leo-nui', 'Giày leo núi chuyên nghiệp', NULL, NOW(), NOW()),
(8, 'Giày bóng chuyền', 'giay-bong-chuyen', 'Giày bóng chuyền chuyên nghiệp', NULL, NOW(), NOW()),
(9, 'Giày cầu lông', 'giay-cau-long', 'Giày cầu lông chuyên nghiệp', NULL, NOW(), NOW()),
(10, 'Giày bóng bàn', 'giay-bong-ban', 'Giày bóng bàn chuyên nghiệp', NULL, NOW(), NOW());

-- Insert Products (chuẩn entity, 25 trường)
INSERT INTO products (
  name, slug, description, price, sale_price, category_id, brand, sku, weight, dimensions, material, stock_quantity,
  is_featured, view_count, is_new, is_bestseller, rating_average, status, meta_title, meta_description, meta_keywords,
  gender, created_at, updated_at
) VALUES
('Nike Air Max 270', 'nike-air-max-270', 'Giày thể thao Nike Air Max 270 với đệm Air Max đặc trưng', 2500000, NULL, 1, 'Nike', 'NK-AM270', 0.5, '10x30x20', 'Mesh và Synthetic', 100, true, 0, true, false, 0, 'ACTIVE', 'Nike Air Max 270', 'Giày thể thao Nike Air Max 270', 'nike, air max, giày thể thao', 'MALE', NOW(), NOW()),
('Adidas Ultraboost 22', 'adidas-ultraboost-22', 'Giày chạy bộ Adidas Ultraboost 22 với công nghệ Boost', 2800000, NULL, 3, 'Adidas', 'AD-UB22', 0.4, '10x30x20', 'Primeknit', 80, true, 0, true, true, 0, 'ACTIVE', 'Adidas Ultraboost 22', 'Giày chạy bộ Adidas Ultraboost 22', 'adidas, ultraboost, giày chạy bộ', 'MALE', NOW(), NOW()),
('Nike Air Force 1', 'nike-air-force-1', 'Giày thể thao cổ điển Nike Air Force 1', 2200000, NULL, 1, 'Nike', 'NK-AF1', 0.6, '10x30x20', 'Leather', 120, true, 0, false, true, 0, 'ACTIVE', 'Nike Air Force 1', 'Giày thể thao Nike Air Force 1', 'nike, air force, giày thể thao', 'UNISEX', NOW(), NOW()),
('Adidas Stan Smith', 'adidas-stan-smith', 'Giày thể thao Adidas Stan Smith thiết kế cổ điển', 1800000, NULL, 1, 'Adidas', 'AD-SS', 0.5, '10x30x20', 'Leather', 90, false, 0, false, false, 0, 'ACTIVE', 'Adidas Stan Smith', 'Giày thể thao Adidas Stan Smith', 'adidas, stan smith, giày thể thao', 'UNISEX', NOW(), NOW()),
('Nike Air Jordan 1', 'nike-air-jordan-1', 'Giày bóng rổ Nike Air Jordan 1 phiên bản đặc biệt', 3500000, NULL, 2, 'Nike', 'NK-AJ1', 0.7, '10x30x20', 'Leather', 50, true, 0, true, true, 0, 'ACTIVE', 'Nike Air Jordan 1', 'Giày bóng rổ Nike Air Jordan 1', 'nike, air jordan, giày bóng rổ', 'MALE', NOW(), NOW()),
('Adidas Predator 20.3', 'adidas-predator-203', 'Giày đá bóng Adidas Predator 20.3', 2100000, NULL, 4, 'Adidas', 'AD-P203', 0.5, '10x30x20', 'Synthetic', 60, false, 0, true, false, 0, 'ACTIVE', 'Adidas Predator 20.3', 'Giày đá bóng Adidas Predator 20.3', 'adidas, predator, giày đá bóng', 'MALE', NOW(), NOW()),
('Nike Tiempo Legend 8', 'nike-tiempo-legend-8', 'Giày đá bóng Nike Tiempo Legend 8', 2300000, NULL, 4, 'Nike', 'NK-TL8', 0.5, '10x30x20', 'Leather', 70, false, 0, true, false, 0, 'ACTIVE', 'Nike Tiempo Legend 8', 'Giày đá bóng Nike Tiempo Legend 8', 'nike, tiempo, giày đá bóng', 'MALE', NOW(), NOW()),
('Adidas X Ghosted', 'adidas-x-ghosted', 'Giày đá bóng Adidas X Ghosted', 2400000, NULL, 4, 'Adidas', 'AD-XG', 0.5, '10x30x20', 'Synthetic', 80, false, 0, true, false, 0, 'ACTIVE', 'Adidas X Ghosted', 'Giày đá bóng Adidas X Ghosted', 'adidas, x ghosted, giày đá bóng', 'MALE', NOW(), NOW()),
('Nike Mercurial Superfly 7', 'nike-mercurial-superfly-7', 'Giày đá bóng Nike Mercurial Superfly 7', 2600000, NULL, 4, 'Nike', 'NK-MS7', 0.5, '10x30x20', 'Synthetic', 90, false, 0, true, false, 0, 'ACTIVE', 'Nike Mercurial Superfly 7', 'Giày đá bóng Nike Mercurial Superfly 7', 'nike, mercurial, giày đá bóng', 'MALE', NOW(), NOW()),
('Adidas Copa Sense', 'adidas-copa-sense', 'Giày đá bóng Adidas Copa Sense', 2500000, NULL, 4, 'Adidas', 'AD-CS', 0.5, '10x30x20', 'Leather', 100, false, 0, true, false, 0, 'ACTIVE', 'Adidas Copa Sense', 'Giày đá bóng Adidas Copa Sense', 'adidas, copa, giày đá bóng', 'MALE', NOW(), NOW());

-- Insert Product Variants
INSERT INTO product_variants (product_id, size, color, sku, stock_quantity, created_at, updated_at) VALUES
(1, '40', 'Đen', 'NK-AM270-40-BLK', 10, NOW(), NOW()),
(1, '41', 'Đen', 'NK-AM270-41-BLK', 15, NOW(), NOW()),
(1, '42', 'Đen', 'NK-AM270-42-BLK', 20, NOW(), NOW()),
(1, '43', 'Đen', 'NK-AM270-43-BLK', 15, NOW(), NOW()),
(1, '44', 'Đen', 'NK-AM270-44-BLK', 10, NOW(), NOW()),
(2, '40', 'Trắng', 'AD-UB22-40-WHT', 12, NOW(), NOW()),
(2, '41', 'Trắng', 'AD-UB22-41-WHT', 18, NOW(), NOW()),
(2, '42', 'Trắng', 'AD-UB22-42-WHT', 22, NOW(), NOW()),
(2, '43', 'Trắng', 'AD-UB22-43-WHT', 18, NOW(), NOW()),
(2, '44', 'Trắng', 'AD-UB22-44-WHT', 12, NOW(), NOW()),
(6, '40', 'Đen', 'NK-ZP38-40-BLK', 10, NOW(), NOW()),
(6, '41', 'Đen', 'NK-ZP38-41-BLK', 15, NOW(), NOW()),
(6, '42', 'Đen', 'NK-ZP38-42-BLK', 20, NOW(), NOW()),
(6, '43', 'Đen', 'NK-ZP38-43-BLK', 15, NOW(), NOW()),
(6, '44', 'Đen', 'NK-ZP38-44-BLK', 10, NOW(), NOW()),
(7, '40', 'Trắng', 'AD-PE-40-WHT', 12, NOW(), NOW()),
(7, '41', 'Trắng', 'AD-PE-41-WHT', 18, NOW(), NOW()),
(7, '42', 'Trắng', 'AD-PE-42-WHT', 22, NOW(), NOW()),
(7, '43', 'Trắng', 'AD-PE-43-WHT', 18, NOW(), NOW()),
(7, '44', 'Trắng', 'AD-PE-44-WHT', 12, NOW(), NOW()),
(8, '40', 'Đen', 'NK-CV-40-BLK', 10, NOW(), NOW()),
(8, '41', 'Đen', 'NK-CV-41-BLK', 15, NOW(), NOW()),
(8, '42', 'Đen', 'NK-CV-42-BLK', 20, NOW(), NOW()),
(8, '43', 'Đen', 'NK-CV-43-BLK', 15, NOW(), NOW()),
(8, '44', 'Đen', 'NK-CV-44-BLK', 10, NOW(), NOW()),
(9, '40', 'Đen', 'AD-PL5-40-BLK', 10, NOW(), NOW()),
(9, '41', 'Đen', 'AD-PL5-41-BLK', 15, NOW(), NOW()),
(9, '42', 'Đen', 'AD-PL5-42-BLK', 20, NOW(), NOW()),
(9, '43', 'Đen', 'AD-PL5-43-BLK', 15, NOW(), NOW()),
(9, '44', 'Đen', 'AD-PL5-44-BLK', 10, NOW(), NOW()),
(10, '40', 'Đen', 'NK-AZVX-40-BLK', 10, NOW(), NOW()),
(10, '41', 'Đen', 'NK-AZVX-41-BLK', 15, NOW(), NOW()),
(10, '42', 'Đen', 'NK-AZVX-42-BLK', 20, NOW(), NOW()),
(10, '43', 'Đen', 'NK-AZVX-43-BLK', 15, NOW(), NOW()),
(10, '44', 'Đen', 'NK-AZVX-44-BLK', 10, NOW(), NOW()),
(11, '40', 'Đen', 'NK-AZVP-40-BLK', 10, NOW(), NOW()),
(11, '41', 'Đen', 'NK-AZVP-41-BLK', 15, NOW(), NOW()),
(11, '42', 'Đen', 'NK-AZVP-42-BLK', 20, NOW(), NOW()),
(11, '43', 'Đen', 'NK-AZVP-43-BLK', 15, NOW(), NOW()),
(11, '44', 'Đen', 'NK-AZVP-44-BLK', 10, NOW(), NOW()),
(12, '40', 'Trắng', 'AD-AU4-40-WHT', 12, NOW(), NOW()),
(12, '41', 'Trắng', 'AD-AU4-41-WHT', 18, NOW(), NOW()),
(12, '42', 'Trắng', 'AD-AU4-42-WHT', 22, NOW(), NOW()),
(12, '43', 'Trắng', 'AD-AU4-43-WHT', 18, NOW(), NOW()),
(12, '44', 'Trắng', 'AD-AU4-44-WHT', 12, NOW(), NOW()),
(13, '40', 'Đen', 'NK-M7-40-BLK', 10, NOW(), NOW()),
(13, '41', 'Đen', 'NK-M7-41-BLK', 15, NOW(), NOW()),
(13, '42', 'Đen', 'NK-M7-42-BLK', 20, NOW(), NOW()),
(13, '43', 'Đen', 'NK-M7-43-BLK', 15, NOW(), NOW()),
(13, '44', 'Đen', 'NK-M7-44-BLK', 10, NOW(), NOW()),
(14, '40', 'Đen', 'AD-AW-40-BLK', 10, NOW(), NOW()),
(14, '41', 'Đen', 'AD-AW-41-BLK', 15, NOW(), NOW()),
(14, '42', 'Đen', 'AD-AW-42-BLK', 20, NOW(), NOW()),
(14, '43', 'Đen', 'AD-AW-43-BLK', 15, NOW(), NOW()),
(14, '44', 'Đen', 'AD-AW-44-BLK', 10, NOW(), NOW()),
(15, '40', 'Đen', 'NK-WH7-40-BLK', 10, NOW(), NOW()),
(15, '41', 'Đen', 'NK-WH7-41-BLK', 15, NOW(), NOW()),
(15, '42', 'Đen', 'NK-WH7-42-BLK', 20, NOW(), NOW()),
(15, '43', 'Đen', 'NK-WH7-43-BLK', 15, NOW(), NOW()),
(15, '44', 'Đen', 'NK-WH7-44-BLK', 10, NOW(), NOW()),
(16, '40', 'Đen', 'AD-TFH-40-BLK', 10, NOW(), NOW()),
(16, '41', 'Đen', 'AD-TFH-41-BLK', 15, NOW(), NOW()),
(16, '42', 'Đen', 'AD-TFH-42-BLK', 20, NOW(), NOW()),
(16, '43', 'Đen', 'AD-TFH-43-BLK', 15, NOW(), NOW()),
(16, '44', 'Đen', 'AD-TFH-44-BLK', 10, NOW(), NOW()),
(17, '40', 'Trắng', 'NK-AZH2-40-WHT', 12, NOW(), NOW()),
(17, '41', 'Trắng', 'NK-AZH2-41-WHT', 18, NOW(), NOW()),
(17, '42', 'Trắng', 'NK-AZH2-42-WHT', 22, NOW(), NOW()),
(17, '43', 'Trắng', 'NK-AZH2-43-WHT', 18, NOW(), NOW()),
(17, '44', 'Trắng', 'NK-AZH2-44-WHT', 12, NOW(), NOW()),
(18, '40', 'Đen', 'AD-SNG-40-BLK', 10, NOW(), NOW()),
(18, '41', 'Đen', 'AD-SNG-41-BLK', 15, NOW(), NOW()),
(18, '42', 'Đen', 'AD-SNG-42-BLK', 20, NOW(), NOW()),
(18, '43', 'Đen', 'AD-SNG-43-BLK', 15, NOW(), NOW()),
(18, '44', 'Đen', 'AD-SNG-44-BLK', 10, NOW(), NOW()),
(19, '40', 'Đen', 'NK-AZVX2-40-BLK', 10, NOW(), NOW()),
(19, '41', 'Đen', 'NK-AZVX2-41-BLK', 15, NOW(), NOW()),
(19, '42', 'Đen', 'NK-AZVX2-42-BLK', 20, NOW(), NOW()),
(19, '43', 'Đen', 'NK-AZVX2-43-BLK', 15, NOW(), NOW()),
(19, '44', 'Đen', 'NK-AZVX2-44-BLK', 10, NOW(), NOW()),
(20, '40', 'Đen', 'AD-AU42-40-BLK', 10, NOW(), NOW()),
(20, '41', 'Đen', 'AD-AU42-41-BLK', 15, NOW(), NOW()),
(20, '42', 'Đen', 'AD-AU42-42-BLK', 20, NOW(), NOW()),
(20, '43', 'Đen', 'AD-AU42-43-BLK', 15, NOW(), NOW()),
(20, '44', 'Đen', 'AD-AU42-44-BLK', 10, NOW(), NOW()),
(21, '40', 'Đen', 'NK-AZVX3-40-BLK', 10, NOW(), NOW()),
(21, '41', 'Đen', 'NK-AZVX3-41-BLK', 15, NOW(), NOW()),
(21, '42', 'Đen', 'NK-AZVX3-42-BLK', 20, NOW(), NOW()),
(21, '43', 'Đen', 'NK-AZVX3-43-BLK', 15, NOW(), NOW()),
(21, '44', 'Đen', 'NK-AZVX3-44-BLK', 10, NOW(), NOW()),
(22, '40', 'Trắng', 'AD-AU43-40-WHT', 12, NOW(), NOW()),
(22, '41', 'Trắng', 'AD-AU43-41-WHT', 18, NOW(), NOW()),
(22, '42', 'Trắng', 'AD-AU43-42-WHT', 22, NOW(), NOW()),
(22, '43', 'Trắng', 'AD-AU43-43-WHT', 18, NOW(), NOW()),
(22, '44', 'Trắng', 'AD-AU43-44-WHT', 12, NOW(), NOW()),
(23, '40', 'Đen', 'NK-AM270-2-40-BLK', 10, NOW(), NOW()),
(23, '41', 'Đen', 'NK-AM270-2-41-BLK', 15, NOW(), NOW()),
(23, '42', 'Đen', 'NK-AM270-2-42-BLK', 20, NOW(), NOW()),
(23, '43', 'Đen', 'NK-AM270-2-43-BLK', 15, NOW(), NOW()),
(23, '44', 'Đen', 'NK-AM270-2-44-BLK', 10, NOW(), NOW()),
(24, '40', 'Trắng', 'AD-UB22-2-40-WHT', 12, NOW(), NOW()),
(24, '41', 'Trắng', 'AD-UB22-2-41-WHT', 18, NOW(), NOW()),
(24, '42', 'Trắng', 'AD-UB22-2-42-WHT', 22, NOW(), NOW()),
(24, '43', 'Trắng', 'AD-UB22-2-43-WHT', 18, NOW(), NOW()),
(24, '44', 'Trắng', 'AD-UB22-2-44-WHT', 12, NOW(), NOW()),
(25, '40', 'Đen', 'NK-AF1-2-40-BLK', 10, NOW(), NOW()),
(25, '41', 'Đen', 'NK-AF1-2-41-BLK', 15, NOW(), NOW()),
(25, '42', 'Đen', 'NK-AF1-2-42-BLK', 20, NOW(), NOW()),
(25, '43', 'Đen', 'NK-AF1-2-43-BLK', 15, NOW(), NOW()),
(25, '44', 'Đen', 'NK-AF1-2-44-BLK', 10, NOW(), NOW());

-- Insert Product Images
INSERT INTO product_images (product_id, image_url, is_primary, created_at) VALUES
(1, 'https://example.com/images/nike-air-max-270-1.jpg', true, NOW()),
(1, 'https://example.com/images/nike-air-max-270-2.jpg', false, NOW()),
(1, 'https://example.com/images/nike-air-max-270-3.jpg', false, NOW()),
(2, 'https://example.com/images/adidas-ultraboost-22-1.jpg', true, NOW()),
(2, 'https://example.com/images/adidas-ultraboost-22-2.jpg', false, NOW()),
(2, 'https://example.com/images/adidas-ultraboost-22-3.jpg', false, NOW()),
(3, 'https://example.com/images/nike-air-force-1-1.jpg', true, NOW()),
(3, 'https://example.com/images/nike-air-force-1-2.jpg', false, NOW()),
(3, 'https://example.com/images/nike-air-force-1-3.jpg', false, NOW()),
(4, 'https://example.com/images/adidas-stan-smith-1.jpg', true, NOW()),
(4, 'https://example.com/images/adidas-stan-smith-2.jpg', false, NOW()),
(4, 'https://example.com/images/adidas-stan-smith-3.jpg', false, NOW()),
(5, 'https://example.com/images/nike-air-jordan-1-1.jpg', true, NOW()),
(5, 'https://example.com/images/nike-air-jordan-1-2.jpg', false, NOW()),
(5, 'https://example.com/images/nike-air-jordan-1-3.jpg', false, NOW()),
(6, 'https://example.com/images/nike-zoom-pegasus-38-1.jpg', true, NOW()),
(6, 'https://example.com/images/nike-zoom-pegasus-38-2.jpg', false, NOW()),
(6, 'https://example.com/images/nike-zoom-pegasus-38-3.jpg', false, NOW()),
(7, 'https://example.com/images/adidas-predator-edge-1.jpg', true, NOW()),
(7, 'https://example.com/images/adidas-predator-edge-2.jpg', false, NOW()),
(7, 'https://example.com/images/adidas-predator-edge-3.jpg', false, NOW()),
(8, 'https://example.com/images/nike-court-vision-1.jpg', true, NOW()),
(8, 'https://example.com/images/nike-court-vision-2.jpg', false, NOW()),
(8, 'https://example.com/images/nike-court-vision-3.jpg', false, NOW()),
(9, 'https://example.com/images/adidas-powerlift-5-1.jpg', true, NOW()),
(9, 'https://example.com/images/adidas-powerlift-5-2.jpg', false, NOW()),
(9, 'https://example.com/images/adidas-powerlift-5-3.jpg', false, NOW()),
(10, 'https://example.com/images/nike-air-zoom-vapor-x-1.jpg', true, NOW()),
(10, 'https://example.com/images/nike-air-zoom-vapor-x-2.jpg', false, NOW()),
(10, 'https://example.com/images/nike-air-zoom-vapor-x-3.jpg', false, NOW()),
(11, 'https://example.com/images/nike-air-zoom-vapor-pro-1.jpg', true, NOW()),
(11, 'https://example.com/images/nike-air-zoom-vapor-pro-2.jpg', false, NOW()),
(11, 'https://example.com/images/nike-air-zoom-vapor-pro-3.jpg', false, NOW()),
(12, 'https://example.com/images/adidas-adizero-ubersonic-4-1.jpg', true, NOW()),
(12, 'https://example.com/images/adidas-adizero-ubersonic-4-2.jpg', false, NOW()),
(12, 'https://example.com/images/adidas-adizero-ubersonic-4-3.jpg', false, NOW()),
(13, 'https://example.com/images/nike-metcon-7-1.jpg', true, NOW()),
(13, 'https://example.com/images/nike-metcon-7-2.jpg', false, NOW()),
(13, 'https://example.com/images/nike-metcon-7-3.jpg', false, NOW()),
(14, 'https://example.com/images/adidas-adipower-weightlifting-1.jpg', true, NOW()),
(14, 'https://example.com/images/adidas-adipower-weightlifting-2.jpg', false, NOW()),
(14, 'https://example.com/images/adidas-adipower-weightlifting-3.jpg', false, NOW()),
(15, 'https://example.com/images/nike-wildhorse-7-1.jpg', true, NOW()),
(15, 'https://example.com/images/nike-wildhorse-7-2.jpg', false, NOW()),
(15, 'https://example.com/images/nike-wildhorse-7-3.jpg', false, NOW()),
(16, 'https://example.com/images/adidas-terrex-free-hiker-1.jpg', true, NOW()),
(16, 'https://example.com/images/adidas-terrex-free-hiker-2.jpg', false, NOW()),
(16, 'https://example.com/images/adidas-terrex-free-hiker-3.jpg', false, NOW()),
(17, 'https://example.com/images/nike-air-zoom-hyperace-2-1.jpg', true, NOW()),
(17, 'https://example.com/images/nike-air-zoom-hyperace-2-2.jpg', false, NOW()),
(17, 'https://example.com/images/nike-air-zoom-hyperace-2-3.jpg', false, NOW()),
(18, 'https://example.com/images/adidas-stabil-next-gen-1.jpg', true, NOW()),
(18, 'https://example.com/images/adidas-stabil-next-gen-2.jpg', false, NOW()),
(18, 'https://example.com/images/adidas-stabil-next-gen-3.jpg', false, NOW()),
(19, 'https://example.com/images/nike-air-zoom-vapor-x-2-1.jpg', true, NOW()),
(19, 'https://example.com/images/nike-air-zoom-vapor-x-2-2.jpg', false, NOW()),
(19, 'https://example.com/images/nike-air-zoom-vapor-x-2-3.jpg', false, NOW()),
(20, 'https://example.com/images/adidas-adizero-ubersonic-4-2-1.jpg', true, NOW()),
(20, 'https://example.com/images/adidas-adizero-ubersonic-4-2-2.jpg', false, NOW()),
(20, 'https://example.com/images/adidas-adizero-ubersonic-4-2-3.jpg', false, NOW()),
(21, 'https://example.com/images/nike-air-zoom-vapor-x-3-1.jpg', true, NOW()),
(21, 'https://example.com/images/nike-air-zoom-vapor-x-3-2.jpg', false, NOW()),
(21, 'https://example.com/images/nike-air-zoom-vapor-x-3-3.jpg', false, NOW()),
(22, 'https://example.com/images/adidas-adizero-ubersonic-4-3-1.jpg', true, NOW()),
(22, 'https://example.com/images/adidas-adizero-ubersonic-4-3-2.jpg', false, NOW()),
(22, 'https://example.com/images/adidas-adizero-ubersonic-4-3-3.jpg', false, NOW()),
(23, 'https://example.com/images/nike-air-max-270-2-1.jpg', true, NOW()),
(23, 'https://example.com/images/nike-air-max-270-2-2.jpg', false, NOW()),
(23, 'https://example.com/images/nike-air-max-270-2-3.jpg', false, NOW()),
(24, 'https://example.com/images/adidas-ultraboost-22-2-1.jpg', true, NOW()),
(24, 'https://example.com/images/adidas-ultraboost-22-2-2.jpg', false, NOW()),
(24, 'https://example.com/images/adidas-ultraboost-22-2-3.jpg', false, NOW()),
(25, 'https://example.com/images/nike-air-force-1-2-1.jpg', true, NOW()),
(25, 'https://example.com/images/nike-air-force-1-2-2.jpg', false, NOW()),
(25, 'https://example.com/images/nike-air-force-1-2-3.jpg', false, NOW());

-- Insert Product Tags
INSERT INTO product_tags (name, slug, created_at) VALUES
('Giày thể thao', 'giay-the-thao', NOW()),
('Giày chạy bộ', 'giay-chay-bo', NOW()),
('Giày bóng rổ', 'giay-bong-ro', NOW()),
('Nike', 'nike', NOW()),
('Adidas', 'adidas', NOW());

-- Insert Product Tag Relations
INSERT INTO product_tag_relations (product_id, tag_id) VALUES
(1, 1), (1, 4), -- Nike Air Max 270
(2, 2), (2, 5), -- Adidas Ultraboost 22
(3, 1), (3, 4), -- Nike Air Force 1
(4, 1), (4, 5), -- Adidas Stan Smith
(5, 3), (5, 4), -- Nike Air Jordan 1
(6, 2), (6, 4), -- Nike Zoom Pegasus 38
(7, 4), (7, 5), -- Adidas Predator Edge
(8, 5), (8, 4), -- Nike Court Vision
(9, 6), (9, 5), -- Adidas Powerlift 5
(10, 5), (10, 4), -- Nike Air Zoom Vapor X
(11, 5), (11, 4), -- Nike Air Zoom Vapor Pro
(12, 5), (12, 5), -- Adidas Adizero Ubersonic 4
(13, 6), (13, 4), -- Nike Metcon 7
(14, 6), (14, 5), -- Adidas Adipower Weightlifting
(15, 7), (15, 4), -- Nike Wildhorse 7
(16, 7), (16, 5), -- Adidas Terrex Free Hiker
(17, 8), (17, 4), -- Nike Air Zoom Hyperace 2
(18, 8), (18, 5), -- Adidas Stabil Next Gen
(19, 9), (19, 4), -- Nike Air Zoom Vapor X
(20, 9), (20, 5), -- Adidas Adizero Ubersonic 4
(21, 10), (21, 4), -- Nike Air Zoom Vapor X
(22, 10), (22, 5), -- Adidas Adizero Ubersonic 4
(23, 1), (23, 4), -- Nike Air Max 270
(24, 2), (24, 5), -- Adidas Ultraboost 22
(25, 1), (25, 4); -- Nike Air Force 1

-- Insert Users (50 users)
INSERT INTO user (username, password, email, full_name, phone, address, role, status, created_at, updated_at) VALUES
('admin', '$2a$10$rDkPvvAFV6GgJjXpYW4qUOQZx5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z', 'admin@example.com', 'Administrator', '0123456789', 'Hà Nội', 'ADMIN', 'ACTIVE', NOW(), NOW()),
('user1', '$2a$10$rDkPvvAFV6GgJjXpYW4qUOQZx5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z', 'user1@example.com', 'Nguyễn Văn A', '0123456781', 'Hà Nội', 'USER', 'ACTIVE', NOW(), NOW()),
('user2', '$2a$10$rDkPvvAFV6GgJjXpYW4qUOQZx5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z', 'user2@example.com', 'Trần Thị B', '0123456782', 'Hồ Chí Minh', 'USER', 'ACTIVE', NOW(), NOW()),
('user3', '$2a$10$rDkPvvAFV6GgJjXpYW4qUOQZx5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z', 'user3@example.com', 'Lê Văn C', '0123456783', 'Đà Nẵng', 'USER', 'ACTIVE', NOW(), NOW()),
('user4', '$2a$10$rDkPvvAFV6GgJjXpYW4qUOQZx5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z', 'user4@example.com', 'Phạm Thị D', '0123456784', 'Hải Phòng', 'USER', 'ACTIVE', NOW(), NOW()),
('user5', '$2a$10$rDkPvvAFV6GgJjXpYW4qUOQZx5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z', 'user5@example.com', 'Hoàng Văn E', '0123456785', 'Cần Thơ', 'USER', 'ACTIVE', NOW(), NOW()),
('user6', '$2a$10$rDkPvvAFV6GgJjXpYW4qUOQZx5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z', 'user6@example.com', 'Đỗ Thị F', '0123456786', 'Hà Nội', 'USER', 'ACTIVE', NOW(), NOW()),
('user7', '$2a$10$rDkPvvAFV6GgJjXpYW4qUOQZx5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z', 'user7@example.com', 'Vũ Văn G', '0123456787', 'Hồ Chí Minh', 'USER', 'ACTIVE', NOW(), NOW()),
('user8', '$2a$10$rDkPvvAFV6GgJjXpYW4qUOQZx5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z', 'user8@example.com', 'Đặng Thị H', '0123456788', 'Đà Nẵng', 'USER', 'ACTIVE', NOW(), NOW()),
('user9', '$2a$10$rDkPvvAFV6GgJjXpYW4qUOQZx5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z', 'user9@example.com', 'Bùi Văn I', '0123456790', 'Hải Phòng', 'USER', 'ACTIVE', NOW(), NOW()),
('user10', '$2a$10$rDkPvvAFV6GgJjXpYW4qUOQZx5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z', 'user10@example.com', 'Ngô Thị K', '0123456791', 'Cần Thơ', 'USER', 'ACTIVE', NOW(), NOW());

-- Insert Orders (50 orders)
INSERT INTO `order` (user_id, total_amount, status, shipping_address, shipping_phone, shipping_name, payment_method, payment_status, created_at, updated_at) VALUES
(2, 2500000, 'COMPLETED', '123 Đường ABC, Quận 1, Hà Nội', '0123456781', 'Nguyễn Văn A', 'COD', 'PAID', NOW(), NOW()),
(3, 2800000, 'COMPLETED', '456 Đường XYZ, Quận 2, Hồ Chí Minh', '0123456782', 'Trần Thị B', 'BANK_TRANSFER', 'PAID', NOW(), NOW()),
(4, 2200000, 'PROCESSING', '789 Đường DEF, Quận 3, Đà Nẵng', '0123456783', 'Lê Văn C', 'COD', 'PENDING', NOW(), NOW()),
(5, 1800000, 'COMPLETED', '321 Đường GHI, Quận 4, Hải Phòng', '0123456784', 'Phạm Thị D', 'BANK_TRANSFER', 'PAID', NOW(), NOW()),
(6, 3500000, 'COMPLETED', '654 Đường JKL, Quận 5, Cần Thơ', '0123456785', 'Hoàng Văn E', 'COD', 'PAID', NOW(), NOW()),
(7, 1900000, 'PROCESSING', '987 Đường MNO, Quận 6, Hà Nội', '0123456786', 'Đỗ Thị F', 'BANK_TRANSFER', 'PENDING', NOW(), NOW()),
(8, 2100000, 'COMPLETED', '147 Đường PQR, Quận 7, Hồ Chí Minh', '0123456787', 'Vũ Văn G', 'COD', 'PAID', NOW(), NOW()),
(9, 2600000, 'COMPLETED', '258 Đường STU, Quận 8, Đà Nẵng', '0123456788', 'Đặng Thị H', 'BANK_TRANSFER', 'PAID', NOW(), NOW()),
(10, 2400000, 'PROCESSING', '369 Đường VWX, Quận 9, Hải Phòng', '0123456790', 'Bùi Văn I', 'COD', 'PENDING', NOW(), NOW()),
(11, 2700000, 'COMPLETED', '741 Đường YZ, Quận 10, Cần Thơ', '0123456791', 'Ngô Thị K', 'BANK_TRANSFER', 'PAID', NOW(), NOW());

-- Insert Order Items (50 order items)
INSERT INTO order_item (order_id, product_id, product_variant_id, quantity, price, created_at, updated_at) VALUES
(1, 1, 1, 1, 2500000, NOW(), NOW()),
(1, 2, 6, 1, 2800000, NOW(), NOW()),
(2, 3, 11, 1, 2200000, NOW(), NOW()),
(2, 4, 16, 1, 1800000, NOW(), NOW()),
(3, 5, 21, 1, 3500000, NOW(), NOW()),
(3, 6, 26, 1, 1900000, NOW(), NOW()),
(4, 7, 31, 1, 2100000, NOW(), NOW()),
(4, 8, 36, 1, 2600000, NOW(), NOW()),
(5, 9, 41, 1, 2400000, NOW(), NOW()),
(5, 10, 46, 1, 2700000, NOW(), NOW()),
(11, 11, 51, 1, 2400000, NOW(), NOW()),
(11, 12, 56, 1, 2300000, NOW(), NOW()),
(11, 13, 61, 1, 2200000, NOW(), NOW()),
(11, 14, 66, 1, 2500000, NOW(), NOW()),
(11, 15, 71, 1, 2800000, NOW(), NOW()),
(16, 16, 76, 1, 2900000, NOW(), NOW()),
(16, 17, 81, 1, 2100000, NOW(), NOW()),
(17, 18, 86, 1, 2300000, NOW(), NOW()),
(17, 19, 91, 1, 2400000, NOW(), NOW()),
(18, 20, 96, 1, 2300000, NOW(), NOW()),
(19, 21, 101, 1, 2200000, NOW(), NOW()),
(20, 22, 106, 1, 2500000, NOW(), NOW());

-- Insert Reviews (50 reviews)
INSERT INTO review (user_id, product_id, rating, comment, status, created_at, updated_at) VALUES
(2, 1, 5, 'Sản phẩm rất tốt, đúng như mô tả', 'APPROVED', NOW(), NOW()),
(3, 2, 4, 'Chất lượng tốt, giao hàng nhanh', 'APPROVED', NOW(), NOW()),
(4, 3, 5, 'Đẹp và thoải mái', 'APPROVED', NOW(), NOW()),
(5, 4, 4, 'Sản phẩm tốt, giá hợp lý', 'APPROVED', NOW(), NOW()),
(6, 5, 5, 'Rất hài lòng với sản phẩm', 'APPROVED', NOW(), NOW()),
(7, 6, 4, 'Chất lượng tốt, đúng size', 'APPROVED', NOW(), NOW()),
(8, 7, 5, 'Sản phẩm đẹp, giao hàng nhanh', 'APPROVED', NOW(), NOW()),
(9, 8, 4, 'Giá tốt, chất lượng tốt', 'APPROVED', NOW(), NOW()),
(10, 9, 5, 'Rất hài lòng với sản phẩm', 'APPROVED', NOW(), NOW()),
(11, 10, 4, 'Sản phẩm tốt, đúng như mô tả', 'APPROVED', NOW(), NOW()),
(11, 11, 5, 'Sản phẩm đẹp, đúng size', 'APPROVED', NOW(), NOW()),
(11, 12, 4, 'Chất lượng tốt, giá hợp lý', 'APPROVED', NOW(), NOW()),
(11, 13, 5, 'Rất hài lòng với sản phẩm', 'APPROVED', NOW(), NOW()),
(11, 14, 4, 'Sản phẩm tốt, đúng như mô tả', 'APPROVED', NOW(), NOW()),
(11, 15, 5, 'Rất hài lòng với sản phẩm', 'APPROVED', NOW(), NOW()),
(16, 16, 5, 'Sản phẩm rất tốt, đúng như mô tả', 'APPROVED', NOW(), NOW()),
(17, 17, 4, 'Chất lượng tốt, giao hàng nhanh', 'APPROVED', NOW(), NOW()),
(18, 18, 5, 'Đẹp và thoải mái', 'APPROVED', NOW(), NOW()),
(19, 19, 4, 'Sản phẩm tốt, giá hợp lý', 'APPROVED', NOW(), NOW()),
(20, 20, 5, 'Rất hài lòng với sản phẩm', 'APPROVED', NOW(), NOW());

-- Insert Cart Items (50 cart items)
INSERT INTO cart_item (user_id, product_id, product_variant_id, quantity, created_at, updated_at) VALUES
(2, 1, 1, 1, NOW(), NOW()),
(3, 2, 6, 1, NOW(), NOW()),
(4, 3, 11, 1, NOW(), NOW()),
(5, 4, 16, 1, NOW(), NOW()),
(6, 5, 21, 1, NOW(), NOW()),
(7, 6, 26, 1, NOW(), NOW()),
(8, 7, 31, 1, NOW(), NOW()),
(9, 8, 36, 1, NOW(), NOW()),
(10, 9, 41, 1, NOW(), NOW()),
(11, 10, 46, 1, NOW(), NOW()),
(11, 11, 51, 1, NOW(), NOW()),
(11, 12, 56, 1, NOW(), NOW()),
(11, 13, 61, 1, NOW(), NOW()),
(11, 14, 66, 1, NOW(), NOW()),
(11, 15, 71, 1, NOW(), NOW()),
(16, 16, 76, 1, NOW(), NOW()),
(17, 17, 81, 1, NOW(), NOW()),
(18, 18, 86, 1, NOW(), NOW()),
(19, 19, 91, 1, NOW(), NOW()),
(20, 20, 106, 1, NOW(), NOW());

-- Insert Wishlist Items (50 wishlist items)
INSERT INTO wishlist_item (user_id, product_id, created_at, updated_at) VALUES
(2, 1, NOW(), NOW()),
(3, 2, NOW(), NOW()),
(4, 3, NOW(), NOW()),
(5, 4, NOW(), NOW()),
(6, 5, NOW(), NOW()),
(7, 6, NOW(), NOW()),
(8, 7, NOW(), NOW()),
(9, 8, NOW(), NOW()),
(10, 9, NOW(), NOW()),
(11, 10, NOW(), NOW()),
(11, 11, NOW(), NOW()),
(11, 12, NOW(), NOW()),
(11, 13, NOW(), NOW()),
(11, 14, NOW(), NOW()),
(11, 15, NOW(), NOW()),
(16, 16, NOW(), NOW()),
(17, 17, NOW(), NOW()),
(18, 18, NOW(), NOW()),
(19, 19, NOW(), NOW()),
(20, 20, NOW(), NOW());

-- Insert Promotions (10 promotions)
INSERT INTO promotion (name, description, discount_percent, start_date, end_date, status, created_at, updated_at) VALUES
('Khuyến mãi tháng 1', 'Giảm giá 20% cho tất cả sản phẩm', 20, '2024-01-01', '2024-01-31', 'ACTIVE', NOW(), NOW()),
('Khuyến mãi tháng 2', 'Giảm giá 15% cho giày thể thao', 15, '2024-02-01', '2024-02-29', 'ACTIVE', NOW(), NOW()),
('Khuyến mãi tháng 3', 'Giảm giá 25% cho giày bóng rổ', 25, '2024-03-01', '2024-03-31', 'ACTIVE', NOW(), NOW()),
('Khuyến mãi tháng 4', 'Giảm giá 10% cho giày chạy bộ', 10, '2024-04-01', '2024-04-30', 'ACTIVE', NOW(), NOW()),
('Khuyến mãi tháng 5', 'Giảm giá 30% cho giày đá bóng', 30, '2024-05-01', '2024-05-31', 'ACTIVE', NOW(), NOW()),
('Khuyến mãi tháng 6', 'Giảm giá 20% cho giày tennis', 20, '2024-06-01', '2024-06-30', 'ACTIVE', NOW(), NOW()),
('Khuyến mãi tháng 7', 'Giảm giá 15% cho giày tập gym', 15, '2024-07-01', '2024-07-31', 'ACTIVE', NOW(), NOW()),
('Khuyến mãi tháng 8', 'Giảm giá 25% cho giày leo núi', 25, '2024-08-01', '2024-08-31', 'ACTIVE', NOW(), NOW()),
('Khuyến mãi tháng 9', 'Giảm giá 10% cho giày bóng chuyền', 10, '2024-09-01', '2024-09-30', 'ACTIVE', NOW(), NOW()),
('Khuyến mãi tháng 10', 'Giảm giá 30% cho giày cầu lông', 30, '2024-10-01', '2024-10-31', 'ACTIVE', NOW(), NOW());

-- Insert Promotion Products (50 promotion products)
INSERT INTO promotion_product (promotion_id, product_id, created_at, updated_at) VALUES
(1, 1, NOW(), NOW()),
(1, 2, NOW(), NOW()),
(1, 3, NOW(), NOW()),
(1, 4, NOW(), NOW()),
(1, 5, NOW(), NOW()),
(2, 6, NOW(), NOW()),
(2, 7, NOW(), NOW()),
(2, 8, NOW(), NOW()),
(2, 9, NOW(), NOW()),
(2, 10, NOW(), NOW()),
(3, 11, NOW(), NOW()),
(3, 12, NOW(), NOW()),
(3, 13, NOW(), NOW()),
(3, 14, NOW(), NOW()),
(3, 15, NOW(), NOW()),
(4, 16, NOW(), NOW()),
(4, 17, NOW(), NOW()),
(4, 18, NOW(), NOW()),
(4, 19, NOW(), NOW()),
(4, 20, NOW(), NOW());

-- Insert Shipping Methods (5 shipping methods)
INSERT INTO shipping_methods (name, description, base_fee, estimated_days, is_active, created_at, updated_at)
VALUES
('Giao hàng nhanh', 'Giao hàng trong vòng 24-48 giờ', 30000, 2, true, NOW(), NOW()),
('Giao hàng tiêu chuẩn', 'Giao hàng trong vòng 3-5 ngày', 20000, 4, true, NOW(), NOW()),
('Giao hàng tiết kiệm', 'Giao hàng trong vòng 5-7 ngày', 15000, 7, true, NOW(), NOW());

-- Insert Payment Methods (5 payment methods)
INSERT INTO payment_method (name, description, status, created_at, updated_at) VALUES
('Tiền mặt', 'Thanh toán khi nhận hàng', 'ACTIVE', NOW(), NOW()),
('Chuyển khoản ngân hàng', 'Chuyển khoản qua ngân hàng', 'ACTIVE', NOW(), NOW()),
('Thẻ tín dụng', 'Thanh toán bằng thẻ tín dụng', 'ACTIVE', NOW(), NOW()),
('Ví điện tử', 'Thanh toán qua ví điện tử', 'ACTIVE', NOW(), NOW()),
('Momo', 'Thanh toán qua Momo', 'ACTIVE', NOW(), NOW()); 