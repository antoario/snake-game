SRC_DIR = src
BIN_DIR = bin
MAIN_CLASS = SnakeGame
MODEL_SRC := $(shell find $ model -name '*.java')
MAIN_SRC := $(shell find $ main -name '*.java')

all: compile run

compile:
	@mkdir -p $(BIN_DIR)
	@javac -d $(BIN_DIR) $(MODEL_SRC) $(MAIN_SRC)

run:
	@java -cp $(BIN_DIR) src.main.$(MAIN_CLASS)
        
clean:
	@rm -rf $(BIN_DIR)
