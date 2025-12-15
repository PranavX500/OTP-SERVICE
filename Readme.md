#  OTP Service (Ecommerce Microservices)

The **OTP Service** is responsible for **OTP validation and email verification** in the Ecommerce Microservices system.

It works in an **event-driven manner using Kafka**, where user email details are consumed from another service, OTP is validated, and verification status is published back to Kafka.

---

#  Tech Stack

* **Spring Boot**
* **Kafka (Producer & Consumer)**
* **Email-based OTP Verification**
* **Spring REST APIs**
* **Microservices Architecture**
* **API Gateway Integration**
* **Maven**
* **Lombok**

---

#  Features Implemented

###  **OTP Validation**

* Validates OTP sent to user email
* Checks OTP against latest consumed user data
* Handles invalid or expired OTP scenarios

###  **Kafka Consumer Integration**

* Consumes user email data from Kafka
* Uses latest email information for OTP validation

###  **Kafka Producer Integration**

* Publishes OTP verification result back to Kafka
* Sends verification status after successful OTP validation

---

#  API Endpoints

All endpoints are exposed through the **API Gateway** under the `/OTP` path.

---

##  Validate OTP

**POST** `/OTP/Validate`

Validates the OTP entered by the user against the email received from Kafka.

###  Request Body

```json
{
  "otp": "123456"
}
```

###  Success Response

```text
Valid
```

###  Error Responses

* **400 Bad Request**

  * No email found for validation
  * Invalid or expired OTP

```text
Invalid or expired OTP
```

---

#  OTP Validation Flow

1. User submits OTP
2. OTP Service fetches latest user email from Kafka
3. OTP is validated using internal logic
4. On success:

   * Verification flag is set
   * Email verification event is produced to Kafka
5. Response is returned to client

---

#  Kafka Events

###  Consumed Events

* User email data (used for OTP validation)

###  Produced Events

* OTP verification status (`verified = true/false`)

---

#  Future Enhancements (To Be Implemented)

###  OTP Expiry Time Configuration

Customizable OTP validity duration.

###  Resend OTP Feature

Allow users to request OTP resend.

###  Multiple Channel Support

OTP via SMS / WhatsApp in addition to email.

###  Rate Limiting

Prevent OTP abuse by limiting requests.

---

##  Author

**Pranav Sharma**
Spring Boot | Kafka | Microservices | Security

---
