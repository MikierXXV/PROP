DESTI_CLASSES = ./EXE/
CODI_FONT = /subgrup-prop2-3/
ORG = ./FONTS/src/

default: class run


$(DESTI_CLASSES):
	mkdir -p $(DESTI_CLASSES)

.PHONY: class
class:
	javac -d ${DESTI_CLASSES} ./FONTS/src/presentacion/*.java -d ${DESTI_CLASSES} ./FONTS/src/persistencia/ControladorPersistencia.java -d ${DESTI_CLASSES} ./FONTS/src/persistencia/preprocessat/*.java -d ${DESTI_CLASSES} ./FONTS/src/dominio/clases/algorithm/collaborativefiltering/*.java -d ${DESTI_CLASSES} ./FONTS/src/dominio/clases/algorithm/contentbasedflitering/*.java -d ${DESTI_CLASSES} ./FONTS/src/dominio/clases/recommendation/*.java -d ${DESTI_CLASSES} ./FONTS/src/dominio/clases/algorithm/k_means/*.java -d ${DESTI_CLASSES} ./FONTS/src/dominio/clases/algorithm/slopeone/*.java	-d ${DESTI_CLASSES} ./FONTS/src/dominio/clases/algorithm/hybrid/*.java -d ${DESTI_CLASSES} ./FONTS/src/dominio/clases/content/*.java  -d ${DESTI_CLASSES} ./FONTS/src/dominio/clases/evaluation/*.java -d ${DESTI_CLASSES} ./FONTS/src/dominio/clases/rating/*.java -d ${DESTI_CLASSES}  ./FONTS/src/dominio/controladores/ControladorDominio.java -d ${DESTI_CLASSES} FONTS/src/presentacion/Main.java
run: 
	java -classpath ${DESTI_CLASSES} presentacion.Main

.PHONY: help
help:
	@echo "Opciones:"
	@echo "class: Compila el código a /EXE"
	@echo "run: Ejecuta el código"

.PHONY: clean
clean:
	rm -rf $(DESTI_CLASSES) 

