# proyectolibrosFX

Para ejecutar el proyecto deben usar 2 carpetas, dentro de la carpeta Proyecto tienen dichas carpetas (bin y lib ,  bd_portable es para la BD tipo fichero).
El archivo .zip contiene todas las carpetas y ficheros para lanzar la aplicación en Windows.  
Se debe lanzar el fichero ejecuta.bat

Tienen más información en mi canal de Youtube, donde se explica el proyecto, librerías y funciones.
https://www.youtube.com/watch?v=g3iih2picyc&list=PLzR9zFokHj4FGO6VcEyAflqURRPT5H2GK

Las versiones de Java:
- Java 11 LTS (JDK y JRE) https://adoptopenjdk.net/
- JavaFX 15 (solo SDK) https://openjfx.io/

Si deseas ejecutar la aplicación en distintos S.O. debes realizar la siguiente:
- Descargar el JavaFX SDK versión 15 del S.O. que desees
- Reemplazar la carpeta BIN de nuestra carpeta Proyecto por la nueva 

Para la compilación es necesario insertar estas instrucciones en el BUILD:
<?xml version="1.0" encoding="UTF-8"?>
<!-- You may freely edit this file. See commented blocks below for -->
<!-- some examples of how to customize the build. -->
<!-- (If you delete it and reopen the project it will be recreated.) -->
<!-- By default, only the Clean and Build commands use this build script. -->
<!-- Commands such as Run, Debug, and Test only use this build script if -->
<!-- the Compile on Save feature is turned off for the project. -->
<!-- You can turn off the Compile on Save (or Deploy on Save) setting -->
<!-- in the project's Project Properties dialog box.-->
<project name="Pruebas_Login_JavaFX" default="default" basedir=".">
    <description>Builds, tests, and runs the project Pruebas_Login_JavaFX.</description>
    <import file="nbproject/build-impl.xml"/>
   
    <target name="-post-jar">
    <property name="store.jar.name" value="appFX"/>  <!-- Puedes cambiar el nombre de la aplicación compilada "appFX" -->
    <property name="store.dir" value="Proyecto"/>    <!-- Puedes cambiar el nombre de la carpeta donde se compila el proyecto "Proyecto" -->
    <property name="store.jar" value="${store.dir}/${store.jar.name}.jar"/>
 
    <echo message="Packaging ${application.title} into a single JAR at ${store.jar}"/>
 
    <jar destfile="${store.dir}/temp_final.jar" filesetmanifest="skip">
        <zipgroupfileset dir="dist" includes="*.jar"/>
        <zipgroupfileset dir="dist/lib" includes="*.jar"/>
              
        <manifest>
            <attribute name="Main-Class" value="${main.class}"/>
        </manifest>
    </jar>
 
    <zip destfile="${store.jar}">
        <zipfileset src="${store.dir}/temp_final.jar"
        excludes="META-INF/*.SF, META-INF/*.DSA, META-INF/*.RSA"/>
    </zip>
 
    <delete file="${store.dir}/temp_final.jar"/>
    <delete dir="${store.dir}/lib"/>
    <delete file="${store.dir}/README.TXT"/>
</target>
    
</project>
