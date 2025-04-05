#!/bin/bash
set -e

# Esperar a que PostgreSQL esté listo
until pg_isready -U postgres; do
  echo "Esperando a PostgreSQL..."
  sleep 2
done

# Conectarse como superusuario para crear o recrear las bases de datos
psql -U postgres <<-EOSQL
  DROP DATABASE IF EXISTS mss_customer;
  CREATE DATABASE mss_customer;
  DROP DATABASE IF EXISTS mss_account;
  CREATE DATABASE mss_account;
EOSQL

# Crear esquema y tablas en mss_customer
psql -U postgres -d mss_customer <<-EOSQL
  CREATE SCHEMA IF NOT EXISTS customer AUTHORIZATION customer;

  CREATE TABLE IF NOT EXISTS persona (
      id SERIAL PRIMARY KEY,
      nombre VARCHAR(100),
      genero VARCHAR(20),
      edad INTEGER,
      identificacion VARCHAR(50),
      direccion VARCHAR(150),
      telefono VARCHAR(20)
  );

  CREATE TABLE IF NOT EXISTS cliente (
      id INTEGER PRIMARY KEY,
      cliente_id VARCHAR(50) UNIQUE NOT NULL,
      contrasena VARCHAR(100) NOT NULL,
      estado BOOLEAN NOT NULL,
      fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      CONSTRAINT fk_cliente_persona FOREIGN KEY(id) REFERENCES persona(id)
  );
EOSQL

# Crear esquema y tablas en mss_account
psql -U postgres -d mss_account <<-EOSQL
  CREATE SCHEMA IF NOT EXISTS customer AUTHORIZATION account;

  CREATE TABLE IF NOT EXISTS cuenta (
      id SERIAL PRIMARY KEY,
      numero_cuenta VARCHAR(255) UNIQUE NOT NULL,
      tipo_cuenta VARCHAR(50) NOT NULL,
      saldo_inicial DOUBLE PRECISION NOT NULL,
      estado VARCHAR(50) NOT NULL,
      cliente_id BIGINT NOT NULL,
      FOREIGN KEY (cliente_id) REFERENCES customer.persona(id) ON DELETE CASCADE
  );

  CREATE TABLE IF NOT EXISTS movimiento (
      id SERIAL PRIMARY KEY,
      fecha DATE NOT NULL,
      tipo_movimiento VARCHAR(50) NOT NULL,
      valor DOUBLE PRECISION NOT NULL,
      saldo DOUBLE PRECISION NOT NULL,
      cuenta_id BIGINT NOT NULL,
      FOREIGN KEY (cuenta_id) REFERENCES cuenta(id) ON DELETE CASCADE
  );
EOSQL
