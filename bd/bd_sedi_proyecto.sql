-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 24-01-2022 a las 21:14:51
-- Versión del servidor: 10.4.21-MariaDB
-- Versión de PHP: 8.0.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bd_sedi_proyecto`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `apertura`
--

CREATE TABLE `apertura` (
  `id` int(11) NOT NULL,
  `fecha_apertura` date DEFAULT NULL,
  `caja_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `caja`
--

CREATE TABLE `caja` (
  `id` int(11) NOT NULL,
  `estado` char(1) DEFAULT NULL,
  `fecha_apertura` datetime NOT NULL,
  `fecha_cierre` datetime NOT NULL,
  `monto_apertura` decimal(11,2) DEFAULT NULL,
  `monto_cierre` decimal(11,2) DEFAULT NULL,
  `usuario_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `caja`
--

INSERT INTO `caja` (`id`, `estado`, `fecha_apertura`, `fecha_cierre`, `monto_apertura`, `monto_cierre`, `usuario_id`) VALUES
(1, 'C', '2022-01-23 11:27:50', '2022-01-23 17:43:34', '200.00', '200.00', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `id` int(11) NOT NULL,
  `correo` varchar(100) DEFAULT NULL,
  `direccion` varchar(150) NOT NULL,
  `documento` varchar(20) NOT NULL,
  `estado` char(1) NOT NULL,
  `monto_compra` decimal(11,2) NOT NULL,
  `nombre` varchar(250) NOT NULL,
  `telefono` varchar(20) NOT NULL,
  `ubigeo` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`id`, `correo`, `direccion`, `documento`, `estado`, `monto_compra`, `nombre`, `telefono`, `ubigeo`) VALUES
(1, NULL, 'CALLE VENUS 155 URB. SANTA ELENA', '73444093', 'A', '0.00', 'CHIA VEINTIMILLA, JENNYFER PAQUITA', '985005426', '14,1401,140101'),
(3, NULL, 'AV. CANAVAL Y MOREYRA NRO. 590 URB. EL PALOMAR, LIMA - LIMA - SAN ISIDRO', '20391166855', 'I', '0.00', 'EPSON PERU S.A', '01-2985183', '15,1501,150131'),
(4, NULL, 'CALLE VENUS 145 URB.SANTA ELENA', '16620936', 'A', '0.00', 'CUMPA SANCHEZ, OSCAR MANUEL', '956322572', '14,1401,140101'),
(5, NULL, 'CALLE VENUS 145 URB SANTA ELENA', '01139746', 'I', '0.00', 'VEINTIMILLA TUESTA, JESUS', '993660243', '14,1401,140101'),
(6, NULL, 'exapledirbyJmontanaro', '835681026719243', 'A', '0.00', 'Jonas Montanaro de Farina', '+571672578192', '. . .'),
(7, NULL, 'RAMA CUSTODIO MZ F LT 65 CPM CALLANCA', '71509556', 'A', '0.00', 'BUENO GONZALES, FRANKLIN', '985178542', '14,1401,140108'),
(8, NULL, 'JR.LEGUIA 380', '01139745', 'A', '0.00', 'RAMIREZ PINEDO, LEONARDO LUIS', '996782153', '22,2209,220901'),
(9, NULL, 'Quedevo Vázquez', '2784159835', 'A', '0.00', 'Kevin Aaron', '+549821583447', '- - -');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `concepto_mov_caja`
--

CREATE TABLE `concepto_mov_caja` (
  `id` int(11) NOT NULL,
  `estado` char(1) NOT NULL,
  `nombre` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `concepto_mov_caja`
--

INSERT INTO `concepto_mov_caja` (`id`, `estado`, `nombre`) VALUES
(1, 'A', 'Pago de planilla'),
(2, 'A', 'Pago de pasajes'),
(3, 'A', 'Gastos de administración'),
(4, 'A', 'Servicio de facturación');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contrato`
--

CREATE TABLE `contrato` (
  `id` int(11) NOT NULL,
  `cuota_mensual` decimal(11,2) NOT NULL,
  `estado` char(1) NOT NULL,
  `fecha_inicio` date NOT NULL,
  `fecha_termino` date NOT NULL,
  `total_contrato` decimal(11,2) NOT NULL,
  `total_cuotas` int(11) NOT NULL,
  `cliente_id` int(11) NOT NULL,
  `tipo_contrato_id` int(11) NOT NULL,
  `cuotas_pagadas` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_caja`
--

CREATE TABLE `detalle_caja` (
  `id` int(11) NOT NULL,
  `monto` decimal(11,2) NOT NULL,
  `caja_id` int(11) NOT NULL,
  `metodo_pago_id` int(11) NOT NULL,
  `monto_cierre` decimal(11,2) NOT NULL,
  `cerrado` bit(1) NOT NULL,
  `fecha_creacion` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empresa`
--

CREATE TABLE `empresa` (
  `id` int(11) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `direccion` varchar(200) NOT NULL,
  `estado` char(1) NOT NULL,
  `fecha_alta` datetime NOT NULL,
  `razon_social` varchar(250) NOT NULL,
  `ruc` varchar(11) NOT NULL,
  `telefono` varchar(15) NOT NULL,
  `ubigeo` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `empresa`
--

INSERT INTO `empresa` (`id`, `correo`, `direccion`, `estado`, `fecha_alta`, `razon_social`, `ruc`, `telefono`, `ubigeo`) VALUES
(1, 'empresa1@gmail.com', 'direccionejemplo', 'A', '2021-12-09 15:27:49', 'Empresa 1', '83561293471', '970778767', '---'),
(2, 'empresa2@gmail.com', 'direccionejemplo', 'A', '2022-01-17 16:06:16', 'Empresa 2', '64926661018', '981682241', '---');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `metodo_pago`
--

CREATE TABLE `metodo_pago` (
  `id` int(11) NOT NULL,
  `cci` varchar(100) NOT NULL,
  `estado` char(1) NOT NULL,
  `nombre` varchar(90) NOT NULL,
  `numero_cuenta` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `metodo_pago`
--

INSERT INTO `metodo_pago` (`id`, `cci`, `estado`, `nombre`, `numero_cuenta`) VALUES
(1, '- - -', 'A', 'efectivo', '- - -'),
(2, '- - -', 'A', 'BCP', '- - -'),
(3, '- - -', 'A', 'BBVA', '- - -');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mov_caja`
--

CREATE TABLE `mov_caja` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(250) NOT NULL,
  `estado` char(1) NOT NULL,
  `tipo_mov` char(1) NOT NULL,
  `total` decimal(11,2) NOT NULL,
  `caja_id` int(11) NOT NULL,
  `concepto_mov_caja_id` int(11) NOT NULL,
  `metodo_pago_id` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  `cliente_id` int(11) DEFAULT NULL,
  `proveedor_id` int(11) DEFAULT NULL,
  `trabajador_id` int(11) DEFAULT NULL,
  `apertura_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pago_contrato`
--

CREATE TABLE `pago_contrato` (
  `id` int(11) NOT NULL,
  `fecha_pago` datetime NOT NULL,
  `id_tipo_pago` int(11) NOT NULL,
  `monto_pagado` decimal(11,2) NOT NULL,
  `numero_cuota` int(11) NOT NULL,
  `caja_id` int(11) NOT NULL,
  `contrato_id` int(11) NOT NULL,
  `metodo_pago_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedor`
--

CREATE TABLE `proveedor` (
  `id` int(11) NOT NULL,
  `correo` varchar(100) DEFAULT NULL,
  `direccion` varchar(150) NOT NULL,
  `documento` varchar(20) NOT NULL,
  `estado` char(1) NOT NULL,
  `monto_compra` decimal(11,2) NOT NULL,
  `nombre` varchar(250) NOT NULL,
  `telefono` varchar(20) NOT NULL,
  `ubigeo` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `proveedor`
--

INSERT INTO `proveedor` (`id`, `correo`, `direccion`, `documento`, `estado`, `monto_compra`, `nombre`, `telefono`, `ubigeo`) VALUES
(1, 'proveddor1@outlook.com', 'dirrrr', '98156283713', 'A', '200.00', 'Proveedor 1', '963813005', '- - -');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_contrato`
--

CREATE TABLE `tipo_contrato` (
  `id` int(11) NOT NULL,
  `estado` char(1) NOT NULL,
  `nombre` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `tipo_contrato`
--

INSERT INTO `tipo_contrato` (`id`, `estado`, `nombre`) VALUES
(1, 'A', 'Facturación electrónica');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` int(11) NOT NULL,
  `contrasenia` varchar(20) NOT NULL,
  `correo` varchar(150) NOT NULL,
  `login` varchar(20) NOT NULL,
  `nombre` varchar(150) NOT NULL,
  `telefono` varchar(15) NOT NULL,
  `empresa_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `contrasenia`, `correo`, `login`, `nombre`, `telefono`, `empresa_id`) VALUES
(1, 'admin123', 'frabugo@gmail.com', 'ocumpaV', 'Franklin Bueno Gonzales', '937813800', 1),
(2, 'jhon9669', 'jhonpablo96@hotmail.com', 'jhon96', 'Jhon Timaná Gonzales', '946519928', 2);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `apertura`
--
ALTER TABLE `apertura`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKkbgw0usgahdsc9cm3u2lra35q` (`caja_id`);

--
-- Indices de la tabla `caja`
--
ALTER TABLE `caja`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_o46orptevvxtf1acptmsnfoxf` (`usuario_id`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `cliente_documento_uindex` (`documento`);

--
-- Indices de la tabla `concepto_mov_caja`
--
ALTER TABLE `concepto_mov_caja`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `contrato`
--
ALTER TABLE `contrato`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKivcfejpecgmfnhk1p8cav4956` (`cliente_id`),
  ADD KEY `FKfvv99c7rjafypivyur599lrpu` (`tipo_contrato_id`);

--
-- Indices de la tabla `detalle_caja`
--
ALTER TABLE `detalle_caja`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK2ekb9c3rutunlf5k49sjasfbu` (`caja_id`),
  ADD KEY `FKqly8w7ckjqle70q9crrmu5s8b` (`metodo_pago_id`);

--
-- Indices de la tabla `empresa`
--
ALTER TABLE `empresa`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_lav9hagh3bprtwk9asbhrkxdj` (`razon_social`),
  ADD UNIQUE KEY `UK_fkso2kbttplho71hoeka6px1s` (`ruc`);

--
-- Indices de la tabla `metodo_pago`
--
ALTER TABLE `metodo_pago`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `mov_caja`
--
ALTER TABLE `mov_caja`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKp3mil8qymo5rltre946bltk0r` (`caja_id`),
  ADD KEY `FKnkgsc5aegh143bxlwxyutcakq` (`concepto_mov_caja_id`),
  ADD KEY `FKgeilwx2ijeqsrubloamhmp6hq` (`metodo_pago_id`),
  ADD KEY `FK2oomcfmd4qvb3xsxmushl1u8m` (`usuario_id`),
  ADD KEY `FKkh3jevv8vyid35knrbaiu1cfl` (`cliente_id`),
  ADD KEY `FKed8oppcjp414m73uy4drgtcyc` (`proveedor_id`),
  ADD KEY `FKrfrckv712tves1a232b91rs2q` (`trabajador_id`),
  ADD KEY `FKf1clarwp4nwxo0qt8iauk5s1s` (`apertura_id`);

--
-- Indices de la tabla `pago_contrato`
--
ALTER TABLE `pago_contrato`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKequb76pxjlij7c56h210e7lu1` (`caja_id`),
  ADD KEY `FK56lxcy9643xqc3dsbkr4cmuco` (`contrato_id`),
  ADD KEY `FKgx695r09dgmro4it1h0573ld` (`metodo_pago_id`);

--
-- Indices de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_4g9u14u8qefcd753ryrpe3foo` (`documento`);

--
-- Indices de la tabla `tipo_contrato`
--
ALTER TABLE `tipo_contrato`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_pxkkfbhkbchlb7g4ybf8e4aqj` (`empresa_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `apertura`
--
ALTER TABLE `apertura`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `caja`
--
ALTER TABLE `caja`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `concepto_mov_caja`
--
ALTER TABLE `concepto_mov_caja`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `contrato`
--
ALTER TABLE `contrato`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `detalle_caja`
--
ALTER TABLE `detalle_caja`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `empresa`
--
ALTER TABLE `empresa`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `metodo_pago`
--
ALTER TABLE `metodo_pago`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `mov_caja`
--
ALTER TABLE `mov_caja`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `pago_contrato`
--
ALTER TABLE `pago_contrato`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `tipo_contrato`
--
ALTER TABLE `tipo_contrato`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `apertura`
--
ALTER TABLE `apertura`
  ADD CONSTRAINT `FKkbgw0usgahdsc9cm3u2lra35q` FOREIGN KEY (`caja_id`) REFERENCES `caja` (`id`);

--
-- Filtros para la tabla `caja`
--
ALTER TABLE `caja`
  ADD CONSTRAINT `FKg3ny2u8nmhcymb0nl7uabcvrf` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`);

--
-- Filtros para la tabla `contrato`
--
ALTER TABLE `contrato`
  ADD CONSTRAINT `FKfvv99c7rjafypivyur599lrpu` FOREIGN KEY (`tipo_contrato_id`) REFERENCES `tipo_contrato` (`id`),
  ADD CONSTRAINT `FKivcfejpecgmfnhk1p8cav4956` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`);

--
-- Filtros para la tabla `detalle_caja`
--
ALTER TABLE `detalle_caja`
  ADD CONSTRAINT `FK2ekb9c3rutunlf5k49sjasfbu` FOREIGN KEY (`caja_id`) REFERENCES `caja` (`id`),
  ADD CONSTRAINT `FKqly8w7ckjqle70q9crrmu5s8b` FOREIGN KEY (`metodo_pago_id`) REFERENCES `metodo_pago` (`id`);

--
-- Filtros para la tabla `mov_caja`
--
ALTER TABLE `mov_caja`
  ADD CONSTRAINT `FK2oomcfmd4qvb3xsxmushl1u8m` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`),
  ADD CONSTRAINT `FKed8oppcjp414m73uy4drgtcyc` FOREIGN KEY (`proveedor_id`) REFERENCES `proveedor` (`id`),
  ADD CONSTRAINT `FKf1clarwp4nwxo0qt8iauk5s1s` FOREIGN KEY (`apertura_id`) REFERENCES `apertura` (`id`),
  ADD CONSTRAINT `FKgeilwx2ijeqsrubloamhmp6hq` FOREIGN KEY (`metodo_pago_id`) REFERENCES `metodo_pago` (`id`),
  ADD CONSTRAINT `FKkh3jevv8vyid35knrbaiu1cfl` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  ADD CONSTRAINT `FKnkgsc5aegh143bxlwxyutcakq` FOREIGN KEY (`concepto_mov_caja_id`) REFERENCES `concepto_mov_caja` (`id`),
  ADD CONSTRAINT `FKp3mil8qymo5rltre946bltk0r` FOREIGN KEY (`caja_id`) REFERENCES `caja` (`id`),
  ADD CONSTRAINT `FKrfrckv712tves1a232b91rs2q` FOREIGN KEY (`trabajador_id`) REFERENCES `usuario` (`id`);

--
-- Filtros para la tabla `pago_contrato`
--
ALTER TABLE `pago_contrato`
  ADD CONSTRAINT `FK56lxcy9643xqc3dsbkr4cmuco` FOREIGN KEY (`contrato_id`) REFERENCES `contrato` (`id`),
  ADD CONSTRAINT `FKequb76pxjlij7c56h210e7lu1` FOREIGN KEY (`caja_id`) REFERENCES `caja` (`id`),
  ADD CONSTRAINT `FKgx695r09dgmro4it1h0573ld` FOREIGN KEY (`metodo_pago_id`) REFERENCES `metodo_pago` (`id`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `FK87ckfs30l64gnivnfk7ywp8l6` FOREIGN KEY (`empresa_id`) REFERENCES `empresa` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
