package lotto.model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class InputParserTest {
    @ParameterizedTest
    @ValueSource(strings = {"1, 2, 3, 4, 5, 6", "1,2,3,4,5,6", "1, 2,3, 4,  5,      6"})
    @DisplayName("예외 발생 X")
    void checkCorrectParsing(String input) {
        assertThatCode(() -> InputParser.parseInput(input))
                .doesNotThrowAnyException();

        assertThat(InputParser.parseInput(input)).isEqualTo(List.of("1", "2", "3", "4", "5", "6"));
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("invalidParameter")
    @DisplayName("파싱 예외 X")
    void checkNoException(String input) {
        assertThatCode(() -> InputParser.parseInput(input))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> invalidParameter() {
        return Stream.of(
                // null, 공백 입력 시 예외 X
                Arguments.of("Null 예외 발생", "1, 2, 3, 4,"),
                Arguments.of("공백 예외 발생", "1, 2, 3, 4, "),

                // 정수가 아닌 값 입력 시 예외 X
                Arguments.of("소수 입력 예외 발생", "1, 2, 3, 4, 0.5"),
                Arguments.of("문자 입력 예외 발생", "1, 2, 3, 4, 안녕"),
                Arguments.of("특수 문자 입력 예외 발생", "1, 2, 3, 4, 5 + 1")
        );
    }
}