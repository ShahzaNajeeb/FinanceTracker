# FinanceTracker
POST http://localhost:8080/api/expenses
  {
    "description": "Lunch",
    "amount": 130,
    "category": "FOOD"
  }
GET http://localhost:8080/api/expenses

GET http://localhost:8080/api/expenses?category=FOOD

GET http://localhost:8080/api/expenses/summary
