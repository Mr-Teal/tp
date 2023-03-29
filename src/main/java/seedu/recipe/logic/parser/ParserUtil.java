package seedu.recipe.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.recipe.commons.core.index.Index;
import seedu.recipe.commons.util.StringUtil;
import seedu.recipe.logic.parser.exceptions.ParseException;
import seedu.recipe.model.recipe.Description;
import seedu.recipe.model.recipe.Ingredient;
import seedu.recipe.model.recipe.SatisfyPriceConditionPredicate;
import seedu.recipe.model.recipe.Step;
import seedu.recipe.model.recipe.Title;
import seedu.recipe.model.tag.Tag;


/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static List<Index> parseIndices(String oneBasedIndices) throws ParseException {
        final String[] indices = oneBasedIndices.split(",");
        final List<Index> indicesList = new ArrayList<>();
        for (String s: indices) {
            indicesList.add(parseIndex(s));
        }
        return indicesList;
    }

    /**
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDesc = description.trim();
        if (!Description.isValidDesc(trimmedDesc)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDesc);
    }

    /**
     * Parses a {@code String step} into a {@code ArrayList<Step>}. Parses each step that is separated by a comma ",".
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code step} is invalid.
     */
    public static List<Step> parseSteps(Collection<String> steps) throws ParseException {
        requireNonNull(steps);
        final List<Step> stepList = new ArrayList<>();
        for (String stepName : steps) {
            stepList.add(parseStepHelper(stepName));
        }
        return stepList;
    }

    /**
     * Parses a {@code String step} into a {@code Step}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code step} is invalid.
     */
    public static Step parseStepHelper(String step) throws ParseException {

        requireNonNull(step);
        String trimmedStep = step.trim();
        if (!Step.isValidStep(trimmedStep)) {
            throw new ParseException(Step.MESSAGE_CONSTRAINTS);
        }
        return new Step(trimmedStep);
    }

    /**
     * Parses a {@code String ingredients} into an {@code ArrayList<Ingredient>}.
     * Ingredients are separated by a comma ","
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code ingredients} is invalid.
     */
    public static Set<Ingredient> parseIngredients(Collection<String> ingredients) throws ParseException {
        requireNonNull(ingredients);
        final Set<Ingredient> ingredientSet = new HashSet<>();
        for (String tagName : ingredients) {
            ingredientSet.add(parseIngredientHelper(tagName));
        }
        return ingredientSet;
    }


    /**
     * Parses a {@code String ingredients} into an {@code Ingredient}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code ingredients} is invalid.
     */
    public static Ingredient parseIngredientHelper(String ingredient) throws ParseException {
        requireNonNull(ingredient);
        String trimmedIngredient = ingredient.trim();
        String[] ingredientFields = trimmedIngredient.split(",");
        if (ingredientFields.length != 4) {
            throw new ParseException(Ingredient.INGREDIENT_WRONG_ARGUMENTS_MESSAGE_CONSTRAINTS);
        }

        String name;
        Double quantity;
        String unitOfMeasurement;
        Double pricePerUnit;

        if (!Ingredient.isValidIngredientName(ingredientFields[0].trim())) {
            throw new ParseException(Ingredient.INGREDIENT_NAME_MESSAGE_CONSTRAINTS);
        } else {
            name = ingredientFields[0].trim();
        }

        try {
            quantity = Double.valueOf(ingredientFields[1].trim());
            if (!Ingredient.isValidIngredientQuantity(quantity)) {
                throw new ParseException(Ingredient.INGREDIENT_QUANTITY_MESSAGE_CONSTRAINTS);
            }
        } catch (NumberFormatException e) {
            throw new ParseException("Quantity is not a double!");
        }


        if (!Ingredient.isValidIngredientUom(ingredientFields[2].trim())) {
            throw new ParseException(Ingredient.INGREDIENT_UOM_MESSAGE_CONSTRAINTS);
        } else {
            unitOfMeasurement = ingredientFields[2].trim();
        }

        try {
            pricePerUnit = Double.valueOf(ingredientFields[3].trim());
            if (!Ingredient.isValidIngredientPpu(pricePerUnit)) {
                throw new ParseException(Ingredient.INGREDIENT_PPU_MESSAGE_CONSTRAINTS);
            }
        } catch (NumberFormatException e) {
            throw new ParseException("Price per unit is not a double!");
        }
        return new Ingredient(name, quantity, unitOfMeasurement, pricePerUnit);
    }

    /**
     * Parses a {@code String title} into an {@code Title}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code title} is invalid.
     */
    public static Title parseTitle(String title) throws ParseException {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        if (!Title.isValidTitle(trimmedTitle)) {
            throw new ParseException(Title.MESSAGE_CONSTRAINTS);
        }
        return new Title(trimmedTitle);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String filterPrice} into a {@code Pair<String, Double}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code String filterPrice} is invalid.
     */
    public static SatisfyPriceConditionPredicate parseFilterPrice(String filterPrice) throws ParseException {
        requireNonNull(filterPrice);
        String trimmedFp = filterPrice.trim();
        String[] fpArr = trimmedFp.split("\\s+");
        if (fpArr.length != 2) {
            throw new ParseException("Incorrect number of arguments.");
        }

        String filter = fpArr[0];
        Double price = 0d;

        if (!filter.equals("<") && !filter.equals(">")) {
            throw new ParseException("Filter symbol is not recognizable.");
        }

        try {
            price = Double.valueOf(fpArr[1]);
            if (price < 0) {
                throw new ParseException("Price less than 0");
            }
        } catch (NumberFormatException e) {
            throw new ParseException("Price is not a valid positive number!");
        }

        return new SatisfyPriceConditionPredicate(filter, price);
    }

    /**
     * Parses {@code String isAsc} into a {@code boolean}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code String isAsc} is invalid.
     */
    public static boolean parseAsc(String isAsc) throws ParseException {
        requireNonNull(isAsc);
        String trimmedIsAsc = isAsc.trim();
        if (trimmedIsAsc.equals("asc")) {
            return true;
        } else if (trimmedIsAsc.equals("desc")) {
            return false;
        } else {
            throw new ParseException("Neither ascending nor descending order!");
        }
    }
}
