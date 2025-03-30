db.usuarios.insertMany([
  {
    "_id": "cliente1@example.com",
    "nombre": "Nombre Cliente 1",
    "email": "cliente1@example.com",
    "edad": 17,
    "direccion": "Dirección Cliente 1"
  },
  {
    "_id": "cliente2@example.com",
    "nombre": "Nombre Cliente 2",
    "email": "cliente2@example.com",
    "edad": 25,
    "direccion": "Dirección Cliente 2"
  },
  {
    "_id": "cliente3@example.com",
    "nombre": "Nombre Cliente 3",
    "email": "cliente3@example.com",
    "edad": 30,
    "direccion": "Dirección Cliente 3"
  }
]);
db.carritos.insertMany([
  {
    "_id": "cliente1@example.com",
    "productos": [
      {
        "nombre": "EA Sports FC 25",
        "cantidad": 1,
        "precio_unitario": 69.99
      },
      {
        "nombre": "The Legend of Zelda: Breath of the Wild",
        "cantidad": 2,
        "precio_unitario": 59.99
      }
    ]
  },
  {
    "_id": "cliente2@example.com",
    "productos": [
      {
        "nombre": "Elden Ring",
        "cantidad": 1,
        "precio_unitario": 59.99
      },
      {
        "nombre": "Cyberpunk 2077",
        "cantidad": 1,
        "precio_unitario": 49.99
      }
    ]
  },
  {
    "_id": "cliente3@example.com",
    "productos": [
      {
        "nombre": "Minecraft",
        "cantidad": 2,
        "precio_unitario": 29.99
      },
      {
        "nombre": "Horizon Forbidden West",
        "cantidad": 1,
        "precio_unitario": 69.99
      }
    ]
  }
]);
db.compras.insertMany([
  {
    "email": "cliente1@example.com",
    "videojuegos": [
      {
        "nombre": "EA Sports FC 25",
        "cantidad": 99,
        "precio_unitario": 69.99
      },
      {
        "nombre": "Elden Ring",
        "cantidad": 3,
        "precio_unitario": 59.99
      }
    ],
    "total": 7108.98,
    "fecha_compra": ISODate("2025-01-01T23:00:00.000Z")
  },
  {
    "email": "cliente2@example.com",
    "videojuegos": [
      {
        "videojuego_id": 3,
        "nombre": "Cyberpunk 2077",
        "cantidad": 2,
        "precio_unitario": 49.99
      },
      {
        "videojuego_id": 5,
        "nombre": "Hollow Knight",
        "cantidad": 1,
        "precio_unitario": 14.99
      }
    ],
    "total": 114.97,
    "fecha_compra": ISODate("2025-02-15T12:30:00.000Z")
  },
  {
    "email": "cliente3@example.com",
    "videojuegos": [
      {
        "nombre": "Minecraft",
        "cantidad": 3,
        "precio_unitario": 29.99
      }
    ],
    "total": 89.97,
    "fecha_compra": ISODate("2025-03-10T18:45:00.000Z")
  }
]);
