# 物品信息模组开发者指南

本文档介绍如何向物品信息模组中添加新的信息类型。

## 架构概述

物品信息模组采用插件化架构，通过 [IItemInfoProvider](file:///D:/Code/ModText\item_info_mod\src\main\java\biantwin\item_info_mod\handler/IItemInfoProvider.java#L3-L12) 接口实现信息提供者的扩展。

### 核心组件

- [IItemInfoProvider](file:///D:/Code/ModText\item_info_mod\src\main\java\biantwin\item_info_mod\handler/IItemInfoProvider.java#L3-L12)：信息提供者接口
- [ItemInfoManager](file:///D:/Code/ModText\item_info_mod\src\main\java\biantwin\item_info_mod/handler/ItemInfoManager.java#L9-L45)：信息管理器，负责调用所有注册的提供者
- [Config](file:///D:/Code/ModText\item_info_mod\src\main\java\biantwin\item_info_mod/Config.java#L5-L112)：配置管理，控制各项功能的开关

## 添加新的信息类型

### 步骤1：创建信息提供者类

创建一个新的类实现 [IItemInfoProvider](file:///D:/Code/ModText\item_info_mod\src\main\java\biantwin\item_info_mod\handler/IItemInfoProvider.java#L3-L12) 接口：

```java
package biantwin.item_info_mod.handler;

import biantwin.item_info_mod.Config;
import biantwin.item_info_mod.util.MessageUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * 示例信息提供者 - 显示物品的某种属性
 */
public class ExampleInfoProvider implements IItemInfoProvider {
    
    @Override
    public boolean provideInfo(Player player, ItemStack itemStack) {
        // 检查功能是否启用
        if (!isEnabled()) {
            return false;
        }
        
        // 检查物品是否满足特定条件（例如：是否是某种类型的物品）
        if (!isApplicable(itemStack)) {
            return false;
        }
        
        // 获取要显示的信息
        String exampleInfo = getExampleInfo(itemStack);
        
        // 发送信息给玩家
        String message = "§a[示例信息] §f物品的示例属性: §b" + exampleInfo;
        MessageUtils.sendCopyableMessageToPlayer(player, message, exampleInfo);
        
        return true;
    }
    
    @Override
    public String getName() {
        return "exampleInfo";
    }
    
    @Override
    public boolean isEnabled() {
        // 返回对应的配置项
        return Config.isEnableExampleInfo();
    }
    
    /**
     * 检查物品是否适用于此信息提供者
     */
    private boolean isApplicable(ItemStack itemStack) {
        // 在这里实现判断逻辑
        // 例如：检查物品类型、NBT数据等
        return true; // 示例：所有物品都适用
    }
    
    /**
     * 获取示例信息
     */
    private String getExampleInfo(ItemStack itemStack) {
        // 在这里实现获取信息的逻辑
        return "示例信息"; // 示例返回值
    }
}
```

### 步骤2：添加配置项

在 [Config.java](file:///D:/Code/ModText\item_info_mod/src\main\java\biantwin\item_info_mod/Config.java) 文件中添加配置项：

1. 在类的成员变量部分添加：

```java
private static ForgeConfigSpec.BooleanValue enableExampleInfoSpec;
public static boolean enableExampleInfo = true; // 默认值
```

2. 在构造函数中添加配置定义：

```java
enableExampleInfoSpec = builder
        .comment("是否启用示例信息功能.")
        .translation("item_info_mod.config.enableExampleInfo")
        .define("enableExampleInfo", true);
```

3. 添加获取配置值的方法：

```java
public static boolean isEnableExampleInfo() {
    try {
        return enableExampleInfoSpec != null ? enableExampleInfoSpec.get() : enableExampleInfo;
    } catch (Exception e) {
        // 如果配置未初始化，返回静态默认值
        return enableExampleInfo;
    }
}
```

4. 在 [sync()](file:///D:/Code/ModText\item_info_mod/src\main\java\biantwin\item_info_mod/Config.java#L103-L109) 方法中添加同步：

```java
enableExampleInfo = enableExampleInfoSpec.get();
```

### 步骤3：添加命令支持（可选）

如果需要通过命令控制新功能，需要在 [CommandHandler.java](file:///D:/Code/ModText\item_info_mod/src\main\java\biantwin\item_info_mod/handler/CommandHandler.java) 中添加命令处理方法：

```java
public static int enableExampleInfo(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
    Config.setEnableExampleInfo(true);
    context.getSource().sendSuccess(() -> Component.literal("§a示例信息显示已启用"), false);
    return 1;
}

public static int disableExampleInfo(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
    Config.setEnableExampleInfo(false);
    context.getSource().sendSuccess(() -> Component.literal("§c示例信息显示已禁用"), false);
    return 1;
}
```

并在 [ItemInfoMod.java](file:///D:/Code/ModText\item_info_mod/src\main\java\biantwin\item_info_mod/ItemInfoMod.java) 的命令注册部分添加新命令。

### 步骤4：注册信息提供者

在 [ItemInfoManager.java](file:///D:/Code/ModText\item_info_mod\src\main\java\biantwin\item_info_mod/handler/ItemInfoManager.java) 的静态初始化块中注册新的提供者：

```java
static {
    // 注册所有信息提供者
    providers.add(new NBTInfoProvider());          // NBT信息（最高优先级）
    providers.add(new EnergyFluidInfoProvider());  // 能源/流体信息
    providers.add(new DurabilityInfoProvider());   // 耐久信息
    providers.add(new FoodInfoProvider());         // 食物信息
    providers.add(new ItemNameInfoProvider());     // 物品名称（最低优先级）
    providers.add(new ExampleInfoProvider());      // 新增：示例信息提供者
}
```

注意：提供者的顺序很重要，因为 [ItemInfoManager](file:///D:/Code/ModText\item_info_mod\src\main\java\biantwin\item_info_mod/handler/ItemInfoManager.java#L9-L45) 会按顺序调用它们。

## 最佳实践

### 信息提供者设计原则

1. **单一职责**：每个信息提供者只负责一种类型的信息
2. **条件检查**：在提供信息前，先检查物品是否符合条件
3. **配置检查**：始终检查功能是否已启用
4. **返回值**：成功提供信息时返回 `true`，否则返回 `false`

### 消息格式规范

- 使用颜色代码增强可读性：
  - `§a` - 绿色（标题）
  - `§f` - 白色（描述）
  - `§b` - 蓝色（数值）
  - `§c` - 红色（警告/错误）
- 标题格式：`§a[信息类别] §f描述: §b值`

### 性能考虑

- 避免在 [provideInfo()](file:///D:/Code/ModText\item_info_mod/src\main\java\biantwin\item_info_mod/handler/IItemInfoProvider.java#L12-L12) 方法中执行耗时操作
- 对复杂计算进行缓存
- 合理利用早期返回（guard clauses）减少不必要的计算

## 调试技巧

1. 使用日志记录信息提供者的执行情况
2. 测试各种物品类型确保信息提供者正确工作
3. 验证配置开关是否按预期工作
4. 检查命令是否能正确控制功能开关

## 常见问题

### 信息不显示
- 检查配置项是否正确启用
- 确认物品是否满足信息提供者的条件
- 验证信息提供者是否已注册

### 多个信息同时显示
- 调整信息提供者的顺序
- 检查条件判断逻辑是否过于宽松

通过遵循本指南，您可以轻松地向物品信息模组添加新的信息类型，同时保持代码的可维护性和扩展性。