SRC_DIR = src
BIN_DIR = bin
MAIN_CLASS = main/SnakeGame
MODEL_SRC := $(shell find $ $(SRC_DIR)/model -name '*.java')
MAIN_SRC := $(shell find $ $(SRC_DIR)/main -name '*.java')

all: compile

compile:
	@mkdir -p $(BIN_DIR)
	@javac -d $(BIN_DIR) $(MODEL_SRC) $(MAIN_SRC)

run: compile
	@java -cp $(BIN_DIR) $(MAIN_CLASS)
        
clean:
	@rm -rf $(BIN_DIR)
