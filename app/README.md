# 📱 App_S9 - Gestión de Perfil y Preferencias

**App_S9** es una aplicación Android educativa desarrollada en Kotlin que permite al usuario gestionar su información personal (perfil), controlar el tema visual (claro/oscuro), y observar la cantidad de veces que ha ingresado a la aplicación.

---

## 🚀 Funcionalidades principales

### 🧑‍💼 Gestión de perfil de usuario

- Guardar y cargar datos personales: nombre, edad y correo electrónico.
- Visualizar la información guardada en una tarjeta (`CardView`).
- Persistencia de datos mediante `SharedPreferences`.

📸 **Captura sugerida:**  

![image](https://github.com/user-attachments/assets/61f8868d-8e84-4721-8fa5-59ebaf40cb2a)


### 🌗 Cambiar entre modo claro y oscuro

- Incluye un `SwitchCompat` para alternar entre temas **claro y oscuro**.
- Guarda la preferencia del usuario entre sesiones.

📸 **Captura sugerida:**  
Modo **claro** 

![image](https://github.com/user-attachments/assets/6a46cf48-4ad9-4139-a24e-8004a41ee2b4)


Modo **oscuro**.

![image](https://github.com/user-attachments/assets/fc6527a1-ed7f-4ac0-acbd-49938b356691)


---

### 📊 Contador de visitas

- La app cuenta automáticamente cuántas veces se ha abierto.
- Se muestra el total en pantalla.
- Permite reiniciar el contador manualmente.

📸 **Captura sugerida:**  

![image](https://github.com/user-attachments/assets/ba716855-f1ff-4712-a5d0-9dc05efab671)

![image](https://github.com/user-attachments/assets/7d2fa732-5cc1-46af-8ceb-034be23d073b)

![image](https://github.com/user-attachments/assets/3288b4d7-9461-4579-8057-5d0ced3f728a)


---

### 🗃️ Gestión de nombre temporal

- El usuario puede escribir su nombre y guardarlo en `SharedPreferences`.
- Luego puede cargarlo para verlo formateado.

📸 **Captura sugerida:**  

![image](https://github.com/user-attachments/assets/e01c46f5-bd5c-4d42-9eb3-9b929e737741)


---

## 🛠️ Tecnologías y librerías utilizadas

- **Kotlin**
- **Android Jetpack**
  - `AppCompat`
  - `Material Components`
  - `ViewBinding`
- **SharedPreferences** (para persistencia de datos)
- **Themes personalizados** (claro y oscuro con Material 3)

---

## 📂 Estructura del proyecto

```plaintext
├── MainActivity.kt            # Pantalla principal: nombre, contador, tema
├── UserProfileActivity.kt    # Pantalla de perfil de usuario
├── SharedPreferencesHelper.kt# Clase helper para manejar SharedPreferences
├── res/
│   ├── layout/
│   │   ├── activity_main.xml
│   │   └── activity_user_profile.xml
│   ├── values/
│   │   ├── strings.xml
│   │   └── themes.xml / themes-night.xml
├── AndroidManifest.xml       # Declaración de actividades
